package dev.anthonynguyen.jee.entities;

import javax.persistence.*;

//region Annotations
@Entity
@Table(name = "items")
@NamedQueries({
    @NamedQuery(name = "BarterItem.all", query = "select barterItem from BarterItem barterItem order by barterItem.id"),
    @NamedQuery(name = "BarterItem.byUser", query = "select barterItem from BarterItem barterItem " +
        "where barterItem.user = :user")
})
//endregion
public class BarterItem {
    //region Properties
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName="id")
    private User user;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "details", nullable = false)
    private String details;

    @Column(name = "image")
    private String image;
    //endregion

    //region Constructors
    public BarterItem() {
    }

    public BarterItem(String title, String details) {
        this.title = title;
        this.details = details;
    }

    public BarterItem(String title, String details, String image, User user) {
        this.title = title;
        this.details = details;
        this.image = image;
        this.user = user;
    }
    //endregion

    //region Getters
    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDetails() {
        return details;
    }

    public String getImage() {
        return image;
    }
    //endregion

    //region Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setImage(String image) {
        this.image = image;
    }
    //endregion
}
