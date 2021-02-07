package dev.anthonynguyen.jee.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
@NamedQueries({
    @NamedQuery(name = "User.all", query = "select us from User us order by us.id"),
    @NamedQuery(name = "User.byUsername", query = "select us from User us where us.username = :username")
})

public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "user_password", nullable = false)
    private String password;

    @Column(name = "user_group", nullable = false)
    private String group;

    public User() {
    }

    public User(String name, String username, String password, String group) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.group = group;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getGroup() {
        return group;
    }
}
