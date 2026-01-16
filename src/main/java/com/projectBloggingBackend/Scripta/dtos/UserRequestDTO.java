package com.projectBloggingBackend.Scripta.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class UserRequestDTO {

    @NotBlank(message = "Name is Required")
    private String userName;

    @NotBlank(message = "Password is Required")
    @Size(min=6,message = "Password must be of more than 6 Characters")
    private String password;

    @Email
    @NotBlank(message = "Email is Required")
    private String email;

    private Set<String> roles;

    public UserRequestDTO(String userName,String password,String email,Set<String>roles){
        this.userName=userName;
        this.email=email;
        this.password=password;
        this.roles=roles;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public String getUserName() {
        return userName;
    }
}
