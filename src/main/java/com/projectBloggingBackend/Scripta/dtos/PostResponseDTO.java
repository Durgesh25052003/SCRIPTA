package com.projectBloggingBackend.Scripta.dtos;

import java.time.LocalDateTime;

public class PostResponseDTO {
    private Long id;
    private String title;
    private String content;
    private String authorUsername;
    private LocalDateTime createdAt;

    public PostResponseDTO(){};

    public PostResponseDTO(int postId,String title,String content,String authorUsername,LocalDateTime createdAt){
        this.authorUsername=authorUsername;
        this.content=content;
        this.id=id;
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

    public void setId(Long id) {
        this.id = id;
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

    public Long getId() {
        return id;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt=createdAt;
    }
}
