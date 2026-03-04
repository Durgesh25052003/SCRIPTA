package com.projectBloggingBackend.Scripta.dtos;

import java.time.LocalDateTime;

public class PostResponseDTO {
    private Long postId;
    private String title;
    private String content;
    private String authorUsername;
    private LocalDateTime createdAt;

    public PostResponseDTO(){};

    public PostResponseDTO(Long postId,String title,String content,String authorUsername,LocalDateTime createdAt){
        this.authorUsername=authorUsername;
        this.content=content;
        this.postId=postId;
        this.title=title;
        this.createdAt=createdAt;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public Long getPostId() {
        return postId;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt=createdAt;
    }
}
