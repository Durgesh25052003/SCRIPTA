package com.projectBloggingBackend.Scripta.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginDTO {

    @NotBlank(message = "Email is Required")
    @Email(message = "Email should be in correct format")
    private String email;

    @NotBlank(message = "Password is Required")
    private String password;

    public LoginDTO(String email,String password){
        this.email=email;
        this.password=password;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}

