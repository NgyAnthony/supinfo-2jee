package dev.anthonynguyen.jee.controller;


import dev.anthonynguyen.jee.services.DataService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;

@RequestScoped
@Named
public class RegisterController {

    //region Properties
    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty
    private String first_name;

    @NotEmpty
    private String last_name;

    @NotEmpty
    private String zipcode;

    @NotEmpty
    private String email;
    //endregion

    //region Services
    @Inject
    FacesContext facesContext;

    @Inject
    SecurityContext securityContext;

    @Inject
    LoginController loginController;

    @Inject
    DataService dataService;
    //endregion

    @PostConstruct
    public void checkIfAlreadyLoggedIn() throws IOException {
        if(facesContext.getExternalContext().getRemoteUser() != null){
            loginController.toProfile();
        }
    }

    //region Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getEmail() {
        return email;
    }

    //endregion

    //region Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //endregion

    public void execute() throws IOException{
        if (processRegister()){
            getExternalContext().redirect(getExternalContext().getRequestContextPath() + "/login.xhtml");
        } else {
            new FacesMessage("Échec de l'inscription !");
        }
    }

    private boolean processRegister(){
        try {
            dataService.createUser(
                first_name, last_name, username, password, "user", email, zipcode
            );
            return true;
        } catch (Exception e){
            return false;
        }
    }

    private ExternalContext getExternalContext(){
        return facesContext.getExternalContext();
    }
}
