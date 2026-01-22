package com.projectBloggingBackend.Scripta.dtos;

import jakarta.validation.constraints.NotBlank;

public class CommentRequestDTO {
    @NotBlank
    private String content;

    public CommentRequestDTO(String content){
        this.content=content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
