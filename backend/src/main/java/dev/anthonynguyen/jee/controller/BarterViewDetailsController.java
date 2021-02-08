package dev.anthonynguyen.jee.controller;

import dev.anthonynguyen.jee.entities.BarterItem;
import dev.anthonynguyen.jee.services.DataService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;

@Named
@RequestScoped
public class BarterViewDetailsController {
    @Inject
    DataService dataService;

    @Inject
    FacesContext facesContext;

    BarterItem barterItem;

    @PostConstruct
    public void init(){
        Map<String, String> parameterMap = getExternalContext().getRequestParameterMap();
        String param = parameterMap.get("id");
        this.setBarterItem(dataService.getBarterItem(Integer.parseInt(param)));
    }

    private ExternalContext getExternalContext(){
        return facesContext.getExternalContext();
    }

    public BarterItem getBarterItem() {
        return barterItem;
    }

    public void setBarterItem(BarterItem barterItem) {
        this.barterItem = barterItem;
    }
}
