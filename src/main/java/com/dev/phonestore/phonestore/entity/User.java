package com.dev.phonestore.phonestore.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "user")
public class User {
    @Id
    private String userName;
    private String userFirstName;
    private String userLastName;
    private String userPassword;
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "USER_ROLE",
            joinColumns = {
                    @JoinColumn(name="USER_ID")
            },
            inverseJoinColumns =  {
                    @JoinColumn(name = "ROLE_ID")
            }
    )
    private Set<Role> roles;
    public User() {}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return "User{" +
                ", userName='" + userName + '\'' +
                ", userFirstName='" + userFirstName + '\'' +
                ", userLastName='" + userLastName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                '}';
    }
}
