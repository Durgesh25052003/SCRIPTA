package com.projectBloggingBackend.Scripta.service;

import com.projectBloggingBackend.Scripta.dtos.UserRequestDTO;
import com.projectBloggingBackend.Scripta.model.Role;
import com.projectBloggingBackend.Scripta.model.User;
import com.projectBloggingBackend.Scripta.repository.ScriptaRoleRepo;
import com.projectBloggingBackend.Scripta.repository.ScriptaUserRepo;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthService {
    private final ScriptaUserRepo userRepo;
    private final ScriptaRoleRepo roleRepo;
    public AuthService(ScriptaUserRepo userRepo,ScriptaRoleRepo roleRepo){
        this.userRepo=userRepo;
        this.roleRepo=roleRepo;
    }

    public User Register(UserRequestDTO userBody){
        User userEntity=mapToEntity(userBody);
        User user=userRepo.save(userEntity);
        return user;
    }
    public User mapToEntity(UserRequestDTO userBodyDTO) {
//        String hashedPassword= passwordEncoder.encode(Student.getPassword());
        User userEntity = new User();
        Set<Role> roleEntities =
                roleRepo.findByRoleNameIn(userBodyDTO.getRoles());

        System.out.println(roleEntities);
        userEntity.setUserName(userBodyDTO.getUserName());
        userEntity.setEmail(userBodyDTO.getEmail());
        userEntity.setPassword(userBodyDTO.getPassword());
        userEntity.setRoles(roleEntities);

        return userEntity;
    }

}
