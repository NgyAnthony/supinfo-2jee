package dev.anthonynguyen.jee.services;

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

    public List<User> getAllUsers() {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> rootEntry = cq.from(User.class);
        CriteriaQuery<User> all = cq.select(rootEntry);

        TypedQuery<User> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }

    @Transactional
    public User createUser(String name, String username, String password, String group){
        User newUser = new User(name, username, passwordHasher.generate(password.toCharArray()), group);
        em.persist(newUser);
        em.flush();
        return newUser;
    }

    public Optional<User> getUser(String username){
        return em.createNamedQuery("User.byUsername", User.class)
            .setParameter("username", username)
            .getResultList()
            .stream()
            .findFirst();
    }
}
