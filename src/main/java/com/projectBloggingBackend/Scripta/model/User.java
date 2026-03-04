package com.projectBloggingBackend.Scripta.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;
    private String userName;
    private String password;
    private String email;

    @ManyToMany(fetch=FetchType.EAGER)
    private Set<Role> roles;
    @CreationTimestamp
    @Column(name="created_at",nullable = false,updatable = false)
    private Date createdAt;

    public User(){};

    public User(String name,String password,String email,Set<Role>roles){
        this.userName=name;
        this.email=email;
        this.password=password;
        this.roles=roles;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Long getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
