package com.projectBloggingBackend.Scripta.service;

import com.projectBloggingBackend.Scripta.dtos.LoginDTO;
import com.projectBloggingBackend.Scripta.dtos.UserRequestDTO;
import com.projectBloggingBackend.Scripta.dtos.UserResponseDTO;
import com.projectBloggingBackend.Scripta.jwt.JWTUtil;
import com.projectBloggingBackend.Scripta.model.Role;
import com.projectBloggingBackend.Scripta.model.User;
import com.projectBloggingBackend.Scripta.repository.ScriptaRoleRepo;
import com.projectBloggingBackend.Scripta.repository.ScriptaUserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthService {
    private final ScriptaUserRepo userRepo;
    private final ScriptaRoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JWTUtil jwtUtil;
    Map<String,String> message=new HashMap<>();
    public AuthService(ScriptaUserRepo userRepo,ScriptaRoleRepo roleRepo,PasswordEncoder passwordEncoder,UserService userService,JWTUtil jwtUtil){
        this.userRepo=userRepo;
        this.roleRepo=roleRepo;
        this.passwordEncoder=passwordEncoder;
        this.userService=userService;
        this.jwtUtil=jwtUtil;
    }

    //RESISTER
    public UserResponseDTO Register(UserRequestDTO userBody) throws Exception{
        User userEntity=mapToEntity(userBody);
        User user=userRepo.save(userEntity);
        return maptoDTO(user);
    }

    //LOGIN

    public ResponseEntity<Map<String,String>> Login(LoginDTO userRequesteDTO) throws Exception {
        //Error Logic is still pending
        System.out.println("✨✨✨");
       User user=userService.getUserByEmail(userRequesteDTO.getEmail());
      if(user==null){
          throw new Exception("User not Found");
      }
      Boolean isPresent=passwordEncoder.matches(userRequesteDTO.getPassword(), user.getPassword());
      List<String>roles= user.getRoles().stream().map(role->role.getRoleName()).toList();
      Map<String, Object>Claims=new HashMap<>();

      Claims.put("roles",roles);
      if(!isPresent){
          message.put("Error","Unauthenticated !.. Access Denied");
          return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
      }
      String token= jwtUtil.createToken(user.getEmail(),Claims);
      message.put("token",token);

      return new ResponseEntity<>(message,HttpStatus.OK);
    }

    public UserResponseDTO maptoDTO(User userEntity){
        List<String> roles=userEntity.getRoles().stream().map(role->role.getRoleName()).toList();
        UserResponseDTO userResponseDTO=new UserResponseDTO();
        userResponseDTO.setUserName(userEntity.getUserName());
        userResponseDTO.setEmail(userEntity.getEmail());
        userResponseDTO.setRoles(roles);
        return userResponseDTO;
    }

    public User mapToEntity(UserRequestDTO userBodyDTO) {
        String hashedPassword;
        hashedPassword = passwordEncoder.encode(userBodyDTO.getPassword());
        User userEntity = new User();
        Set<Role> roleEntities =
                roleRepo.findByRoleNameIn(userBodyDTO.getRoles());

        System.out.println(roleEntities);
        userEntity.setUserName(userBodyDTO.getUserName());
        userEntity.setEmail(userBodyDTO.getEmail());
        userEntity.setPassword(hashedPassword);
        userEntity.setRoles(roleEntities);

        return userEntity;
    }

}
