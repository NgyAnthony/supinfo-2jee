package dev.anthonynguyen.jee.controller;
import dev.anthonynguyen.jee.entities.User;
import dev.anthonynguyen.jee.services.DataService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@RequestScoped
@Named
public class UsersController {

    @Inject
    DataService dataService;

    private List<User> allUsers;

    @PostConstruct
    public void initialize(){
        this.allUsers = dataService.getAllUsers();
        System.out.println(this.allUsers);
    }

    public List<User> getAllUsers() {
        return allUsers;
    }
}
