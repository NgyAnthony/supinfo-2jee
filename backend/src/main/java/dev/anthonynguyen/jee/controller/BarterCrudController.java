package dev.anthonynguyen.jee.controller;

import dev.anthonynguyen.jee.entities.BarterItem;
import dev.anthonynguyen.jee.services.DataService;
import org.primefaces.event.RowEditEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.SystemException;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RequestScoped
@Named
public class BarterCrudController {
    @Inject
    DataService dataService;

    @Inject
    ProfileController profileController;

    private List<BarterItem> barterItemsOwned;

    //region Properties
    private String title;

    private String details;

    private String image;
    //endregion

    //region Getters
    public String getTitle() {
        return title;
    }

    public String getDetails() {
        return details;
    }

    public String getImage() {
        return image;
    }
    //endregion

    //region Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setImage(String image) {
        this.image = image;
    }
    //endregion

    @PostConstruct
    public void initialize(){
        this.barterItemsOwned = dataService.getBarterItemListByUser(profileController.getCurrentUser());
    }

    public List<BarterItem> getAllBarterItemsOwned() {
        return barterItemsOwned;
    }

    public void onRowEdit(RowEditEvent<BarterItem> event) {
        BarterItem item = event.getObject();
        FacesMessage msg = new FacesMessage("Objet modifié", String.valueOf(item.getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
        updateBarterItem(item);
    }

    public void onRowCancel(RowEditEvent<BarterItem> event) {
        FacesMessage msg = new FacesMessage("Modification annulée", String.valueOf(event.getObject().getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void deleteBarterItem(BarterItem item){
        dataService.deleteBarterItem(item);
    }

    public void addBarterItem() {
        dataService.createBarterItem(title, details, image, profileController.getCurrentUser());
    }

    public void updateBarterItem(BarterItem item) {
        dataService.updateBarterItem(item);
    }
}
