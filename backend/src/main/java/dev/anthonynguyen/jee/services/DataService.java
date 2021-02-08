package dev.anthonynguyen.jee.services;

import dev.anthonynguyen.jee.entities.BarterItem;
import dev.anthonynguyen.jee.entities.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class DataService {

    @PersistenceContext(unitName = "SupinfoDS")
    EntityManager em;

    @Inject
    Pbkdf2PasswordHash passwordHasher;

    //region User related CRUD
    public List<User> getAllUsers() {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> rootEntry = cq.from(User.class);
        CriteriaQuery<User> all = cq.select(rootEntry);

        TypedQuery<User> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }

    public Long getUserCount(){
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        // Get the total number of entities
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(User.class)));

        return em.createQuery(countQuery).getSingleResult();
    }

    @Transactional
    public User createUser(String first_name, String last_name, String username, String password,
                           String group, String email, String zipcode){
        User newUser = new User(first_name, last_name, username, passwordHasher.generate(password.toCharArray()),
            group, email, zipcode);
        em.persist(newUser);
        em.flush();
        return newUser;
    }

    // [BarterCrudController] Update a user
    @Transactional
    public void updateUser(User user) {
        em.merge(user);
    }

    public Optional<User> getUser(String username){
        return em.createNamedQuery("User.byUsername", User.class)
            .setParameter("username", username)
            .getResultList()
            .stream()
            .findFirst();
    }
    //endregion

    //region Barter related CRUD

    // [BarterCrudController] Create a barter item
    @Transactional
    public void createBarterItem(String title, String details, String image, User user){
        BarterItem newBarterItem = new BarterItem(title, details, image, user);
        em.persist(newBarterItem);
        em.flush();
    }

    // [BarterCrudController] Get all barter items owned by user
    public List<BarterItem> getBarterItemListByUser(User user){
        return em.createNamedQuery("BarterItem.byUser", BarterItem.class)
            .setParameter("user", user)
            .getResultList();
    }

    // [BarterCrudController] Update a barter item
    @Transactional
    public void updateBarterItem(BarterItem item) {
        em.merge(item);
    }

    // [BarterCrudController] Delete a barter item
    @Transactional
    public void deleteBarterItem(BarterItem item) {
        em.remove(em.contains(item) ? item : em.merge(item));
    }

    // [BarterDetailsController] Find a barter item by ID
    @Transactional
    public BarterItem getBarterItem(Integer id){
        return em.find(BarterItem.class, id);
    }

    public Long getBarterItemCount(){
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        // Get the total number of entities
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(BarterItem.class)));

        return em.createQuery(countQuery).getSingleResult();
    }

    // [IndexController] Pagination query
    public List<BarterItem> getBarterItemPaged(Integer pageNumber, Integer pageSize) {
        //https://www.baeldung.com/jpa-pagination
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        Long count = getBarterItemCount();

        // Selecting the class used by the criteria query
        CriteriaQuery<BarterItem> criteriaQuery = criteriaBuilder.createQuery(BarterItem.class);
        Root<BarterItem> from = criteriaQuery.from(BarterItem.class);
        CriteriaQuery<BarterItem> select = criteriaQuery.select(from);

        // Getting the actual page
        TypedQuery<BarterItem> typedQuery = em.createQuery(select);
        // While loop is used to progress through number of entities and end up at the correct index
        while (pageNumber < count.intValue()) {
            typedQuery.setFirstResult(pageNumber - 1);
            typedQuery.setMaxResults(pageSize);
            pageNumber += pageSize;
        }

        return typedQuery.getResultList();
    }

    // [IndexController] All barter items query
    public List<BarterItem> getAllBarterItems() {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<BarterItem> cq = cb.createQuery(BarterItem.class);
        Root<BarterItem> rootEntry = cq.from(BarterItem.class);
        CriteriaQuery<BarterItem> all = cq.select(rootEntry);

        TypedQuery<BarterItem> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }
    //endregion
}
