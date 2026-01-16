package com.projectBloggingBackend.Scripta.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
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

    public User(String name,String password,String email,Set<Role>roles){
        this.userName=name;
        this.email=email;
        this.password=password;
        this.roles=roles;
    }

}
