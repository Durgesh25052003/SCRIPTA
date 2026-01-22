package com.projectBloggingBackend.Scripta.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "post_id",nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "author_id",nullable = false)
    private User author;

    private LocalDateTime createdAt;

    public Comment(){};

    public Comment(String content,User user,Post post){
        this.content=content;
        this.author=user;
        this.post=post;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getContent() {
        return content;
    }

    public Long getCommentId() {
        return commentId;
    }

    public Post getPost() {
        return post;
    }

    public User getAuthor() {
        return author;
    }
}
