package com.projectBloggingBackend.Scripta.dtos;

import java.time.LocalDateTime;

public class CommentResponseDTO {
        private Long id;
        private String content;
        private String authorUsername; // or authorName
        private LocalDateTime createdAt;
        private long postId;

    public CommentResponseDTO(){};

    public CommentResponseDTO(String content,String authorUsername,LocalDateTime createdAt){
        this.content=content;
        this.authorUsername=authorUsername;
        this.createdAt=createdAt;
    }


    public void setPostId(long postId) {
        this.postId = postId;
    }

    public long getPostId() {
        return postId;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }
}
