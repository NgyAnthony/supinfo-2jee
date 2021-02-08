package dev.anthonynguyen.jee.controller;

import dev.anthonynguyen.jee.entities.User;
import dev.anthonynguyen.jee.services.DataService;

import java.io.IOException;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;

@RequestScoped
@Named
public class LoginController {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @Inject
    FacesContext facesContext;

    @Inject
    SecurityContext securityContext;

    @PostConstruct
    public void redirectIfLoggedIn() throws IOException {
        if(checkIfAlreadyLoggedIn()){
            toProfile();
        }
    }

    public boolean checkIfAlreadyLoggedIn() {
        return facesContext.getExternalContext().getRemoteUser() != null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void toProfile() throws IOException {
        getExternalContext().redirect(getExternalContext().getRequestContextPath() + "/app/profile.xhtml");
    }
    public void execute() throws IOException{
        switch(processAuthentication()){
            case SEND_CONTINUE:
                facesContext.responseComplete();
                break;
            case SEND_FAILURE:
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Credentials", null));
                break;
            case SUCCESS:
                toProfile();
                break;
        }
    }

    private AuthenticationStatus processAuthentication(){
        ExternalContext ec = getExternalContext();
        return securityContext.authenticate((HttpServletRequest)ec.getRequest(),
            (HttpServletResponse)ec.getResponse(),
            AuthenticationParameters.withParams().credential(new UsernamePasswordCredential(username, password)));
    }

    private ExternalContext getExternalContext(){
        return facesContext.getExternalContext();
    }
}
