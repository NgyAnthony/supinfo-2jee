package dev.anthonynguyen.jee.controller;

import dev.anthonynguyen.jee.entities.BarterItem;
import dev.anthonynguyen.jee.entities.User;
import dev.anthonynguyen.jee.entities.UserRole;
import dev.anthonynguyen.jee.services.DataService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@RequestScoped
@Named
public class ProfileController {

    @Inject
    DataService dataService;

    @Inject
    SecurityContext securityContext;

    @Inject
    FacesContext facesContext;

    private Optional<User> currentUser;

    @PostConstruct
    public void initialize(){
        String username = securityContext.getCallerPrincipal().getName();
        this.currentUser = dataService.getUser(username);
    }

    public void reload() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }

    public User getCurrentUser(){
        return currentUser.orElse(null);
    }

    public boolean isAdmin(){
        System.out.println(securityContext.isCallerInRole(UserRole.admin.toString()));
        return securityContext.isCallerInRole(UserRole.admin.toString());
    }

    public String logout() throws ServletException{
        ExternalContext ec = facesContext.getExternalContext();
        ((HttpServletRequest)ec.getRequest()).logout();
        return "/login.xhtml?faces-redirect=true";
    }

    public void updateUser(User user) throws IOException {
        dataService.updateUser(user);
        reload();
    }

}
