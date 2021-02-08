package dev.anthonynguyen.jee.entities;

import javax.persistence.*;
import java.io.Serializable;

//region Annotations
@Entity
@Table(name = "users")
@NamedQueries({
    @NamedQuery(name = "User.all", query = "select us from User us order by us.id"),
    @NamedQuery(name = "User.byUsername", query = "select us from User us where us.username = :username")
})
//endregion
public class User implements Serializable {
    //region Properties
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name", nullable = false)
    private String first_name;

    @Column(name = "last_name", nullable = false)
    private String last_name;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "user_password", nullable = false)
    private String password;

    @Column(name = "user_group", nullable = false)
    private String group;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "zipcode", nullable = false)
    private String zipcode;
    //endregion

    //region Constructors
    public User() {
    }

    public User(String first_name, String last_name, String username, String password,
                String group, String email, String zipcode) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.password = password;
        this.group = group;
        this.email = email;
        this.zipcode = zipcode;
    }
    //endregion

    //region Getters
    public Integer getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
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

    public String getEmail() {
        return email;
    }

    public String getZipcode() {
        return zipcode;
    }
    //endregion

    //region Setters

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    //endregion
}
