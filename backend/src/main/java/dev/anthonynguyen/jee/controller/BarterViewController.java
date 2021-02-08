package dev.anthonynguyen.jee.controller;

import dev.anthonynguyen.jee.entities.BarterItem;
import dev.anthonynguyen.jee.entities.User;
import dev.anthonynguyen.jee.services.DataService;
import org.primefaces.PrimeFaces;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;


@Named
@ViewScoped
public class BarterViewController implements Serializable {

    private List<BarterItem> barterItems;
    private BarterItem selectedBarterItem;

    @Inject
    private DataService dataService;

    @Inject
    private LoginController loginController;

    @Inject
    private ProfileController profileController;

    private Integer numberOfItems;
    private Integer numberOfUsers;

    @Inject
    FacesContext facesContext;

    private User currentUser;

    @PostConstruct
    public void init() {
        // Statistics
        setNumberOfItems(dataService.getBarterItemCount().intValue());
        setNumberOfUsers(dataService.getUserCount().intValue());

        // Get all items
        barterItems = dataService.getAllBarterItems();

        // Get current user
        if(checkIfAlreadyLoggedIn()){
            this.currentUser = profileController.getCurrentUser();
        } else {
            this.currentUser = new User();
            this.currentUser.setFirst_name("Visiteur");
        }
    }

    public boolean checkIfAlreadyLoggedIn() {
        return facesContext.getExternalContext().getRemoteUser() != null;
    }

    //region Getters
    public User getCurrentUser() {
        return currentUser;
    }

    public List<BarterItem> getBarterItems() {
        return barterItems;
    }

    public BarterItem getSelectedBarterItem() {
        return selectedBarterItem;
    }

    public Integer getNumberOfItems() {
        return numberOfItems;
    }

    public Integer getNumberOfUsers() {
        return numberOfUsers;
    }
    //endregion

    //region Setters
    public void setSelectedBarterItem(BarterItem selectedBarterItem) {
        this.selectedBarterItem = selectedBarterItem;
    }

    public void setNumberOfItems(Integer numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public void setNumberOfUsers(Integer numberOfUsers) {
        this.numberOfUsers = numberOfUsers;
    }
    //endregion
}
