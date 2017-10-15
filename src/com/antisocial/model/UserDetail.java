package com.antisocial.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name="user_detail")
public class UserDetail {

    public UserDetail(){

    }

    public UserDetail(String type, String userBio){
        this.type = type;
        this.userBio = userBio;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long ID;

    @OneToOne(mappedBy = "userDetail", cascade = CascadeType.ALL)
    private User user;

    @Column(name="type")
    private String type;

    @Column(name="user_bio")
    @Type(type = "text")
    private String userBio;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserBio() {
        return userBio;
    }

    public void setUserBio(String userBio) {
        this.userBio = userBio;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserDetail{" +
                "ID=" + ID +
                ", user=" + "user object" +
                ", type='" + type + '\'' +
                ", userBio='" + userBio + '\'' +
                '}';
    }
}
