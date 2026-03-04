package com.projectBloggingBackend.Scripta.dtos;

import jakarta.validation.constraints.NotBlank;

public class PostRequestDTO {
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    public PostRequestDTO(){};

    public PostRequestDTO(String title,String content){
        this.content=content;
        this.title=title;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
