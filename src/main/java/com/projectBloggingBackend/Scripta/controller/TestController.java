package com.projectBloggingBackend.Scripta.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/scripta/test")
public class TestController {

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getRoleFeedback")
    public String getRoleFeedback(){
        return "Admin";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/getRoleFeedbackUser")
    public String getRoleFeedbackUser(){
        return "User";
    }
}
