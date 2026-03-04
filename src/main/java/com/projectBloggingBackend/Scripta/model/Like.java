package com.projectBloggingBackend.Scripta.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(
        name="likes",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"post_id","user_id"})
        }
)
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long likeId;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;


    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "post_id",nullable = false)
    private Post post;

    @CreationTimestamp
    @Column(name="created_at",nullable = false,updatable = false)
    private LocalDateTime createdAt;

    public Like(){};

    public Like(Post post, User user){
        this.post=post;
        this.user=user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public long getLikeId() {
        return likeId;
    }

    public Post getPost_id() {
        return post;
    }

    public User getUser_id() {
        return user;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setLikeId(long likeId) {
        this.likeId = likeId;
    }

    public void setPost_id(Post post_id) {
        this.post = post_id;
    }

    public void setUser_id(User user_id) {
        this.user = user_id;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

}
