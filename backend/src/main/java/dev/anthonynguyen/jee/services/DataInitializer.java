package dev.anthonynguyen.jee.services;

import dev.anthonynguyen.jee.entities.User;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class DataInitializer {

    @Inject
    DataService dataService;

    public void execute(@Observes @Initialized(ApplicationScoped.class) Object event){
        if(dataService.getAllUsers().isEmpty()){
            User admin = dataService.createUser("Sally Addams", "admin", "admin", "admin");
            User user = dataService.createUser("Tom Matthews", "user", "user", "user");
        }
    }
}
