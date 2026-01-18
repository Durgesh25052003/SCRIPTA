package com.projectBloggingBackend.Scripta.dtos;

import java.util.List;
import java.util.Set;

public class UserResponseDTO {

    private String userName;

    private String email;

    private List<String> roles;

    public UserResponseDTO(){};

    public UserResponseDTO(String userName,String email,List<String>roles){
        this.userName=userName;
        this.email=email;
        this.roles=roles;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
