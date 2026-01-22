package com.projectBloggingBackend.Scripta.exception;

public class ForbiddenAccess extends RuntimeException{
    public ForbiddenAccess(String message){
        super(message);
    }
}
