package com.projectBloggingBackend.Scripta.controller;

import com.projectBloggingBackend.Scripta.dtos.LoginDTO;
import com.projectBloggingBackend.Scripta.dtos.UserRequestDTO;
import com.projectBloggingBackend.Scripta.dtos.UserResponseDTO;
import com.projectBloggingBackend.Scripta.model.User;
import com.projectBloggingBackend.Scripta.repository.ScriptaUserRepo;
import com.projectBloggingBackend.Scripta.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/scripta/auth")
public class ScriptaAuthController {
    private final AuthService authService;
    public ScriptaAuthController(AuthService authService){
        this.authService=authService;
    }

    @GetMapping("/test")
    public ResponseEntity<String> intialize(){
        return new ResponseEntity<>("Scripta Backend", HttpStatus.OK);
    }

    @PostMapping("/register")
    public UserResponseDTO register(@Validated @RequestBody UserRequestDTO userBody) throws Exception {
        System.out.println(userBody);
        return authService.Register(userBody);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Validated @RequestBody LoginDTO userBody) throws Exception {
        System.out.println("Login");
        System.out.println("✨✨✨");
        return authService.Login(userBody);
    }
}
