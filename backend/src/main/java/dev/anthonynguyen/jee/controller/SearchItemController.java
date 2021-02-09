package dev.anthonynguyen.jee.controller;

import dev.anthonynguyen.jee.entities.BarterItem;
import dev.anthonynguyen.jee.services.DataService;
import org.primefaces.event.SelectEvent;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestScoped
@Named
public class SearchItemController {
    private BarterItem itemObj;
    private List<BarterItem> selectedItems;

    @Inject
    DataService dataService;

    @Inject
    FacesContext facesContext;

    public BarterItem getItemObj() {
        return itemObj;
    }

    public void setItemObj(BarterItem itemObj) {
        this.itemObj = itemObj;
    }

    public List<BarterItem> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(List<BarterItem> selectedItems) {
        this.selectedItems = selectedItems;
    }

    public List<BarterItem> completeText(String query) {
        String queryLowerCase = query.toLowerCase();
        List<BarterItem> items = dataService.getAllBarterItems();

        return items.stream().filter(t -> t.getTitle().toLowerCase().startsWith(queryLowerCase)).collect(Collectors.toList());
    }

    public void onItemSelect(SelectEvent<BarterItem> event) throws IOException {
        getExternalContext().redirect(getExternalContext().getRequestContextPath()
            + "/item-details.xhtml?id=" + event.getObject().getId() );
    }

    private ExternalContext getExternalContext(){
        return facesContext.getExternalContext();
    }
}
