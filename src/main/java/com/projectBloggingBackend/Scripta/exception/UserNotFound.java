package com.projectBloggingBackend.Scripta.exception;

import com.projectBloggingBackend.Scripta.model.User;

public class UserNotFound extends RuntimeException{
    public UserNotFound(String message){
        super(message);
    }
}
