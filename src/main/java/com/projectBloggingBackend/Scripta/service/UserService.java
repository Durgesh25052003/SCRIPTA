package com.projectBloggingBackend.Scripta.service;

import com.projectBloggingBackend.Scripta.model.User;
import com.projectBloggingBackend.Scripta.repository.ScriptaUserRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final ScriptaUserRepo userRepo;

    public UserService(ScriptaUserRepo userRepo){
        this.userRepo=userRepo;
    }

    public User getUserByEmail(String email) throws Exception {
      return userRepo.findByEmail(email);
    }

}
