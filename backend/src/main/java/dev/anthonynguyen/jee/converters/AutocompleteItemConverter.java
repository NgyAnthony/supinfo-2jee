package dev.anthonynguyen.jee.converters;

import dev.anthonynguyen.jee.entities.BarterItem;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("barterItemConverter")
public class AutocompleteItemConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value)
    {
        BarterItem myPOJO = new BarterItem();
        myPOJO.setTitle(value);
        myPOJO.setId( getPOJOId() );
        return myPOJO;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((BarterItem) value).getTitle());
        }
    }

    protected int getPOJOId()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        BarterItem pojo = context.getApplication()
            .evaluateExpressionGet(context, "#{searchItemController.itemObj}", BarterItem.class);
        return pojo.getId();
    }
}
