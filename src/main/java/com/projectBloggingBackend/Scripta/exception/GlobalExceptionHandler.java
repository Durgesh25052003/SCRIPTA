package com.projectBloggingBackend.Scripta.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>>HandleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        Map<String,String>errorMessage=new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error-> errorMessage.put(error.getField(),error.getDefaultMessage()));
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<String> HandleUserNotFound(UserNotFound exception){
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ForbiddenAccess.class)
    public ResponseEntity<String>HandleForbiddenAccess(ForbiddenAccess forbiddenAccess){
        return new ResponseEntity<>(forbiddenAccess.getMessage(),HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<String>ResourceNotFound(ResourceNotFound resourceNotFound){
        return new ResponseEntity<>(resourceNotFound.getMessage(),HttpStatus.NOT_FOUND);
    }
}
