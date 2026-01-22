package com.projectBloggingBackend.Scripta.controller;

import com.projectBloggingBackend.Scripta.dtos.PostRequestDTO;
import com.projectBloggingBackend.Scripta.dtos.PostResponseDTO;
import com.projectBloggingBackend.Scripta.dtos.UserResponseDTO;
import com.projectBloggingBackend.Scripta.repository.ScriptaPostRepo;
import com.projectBloggingBackend.Scripta.repository.ScriptaRoleRepo;
import com.projectBloggingBackend.Scripta.repository.ScriptaUserRepo;
import com.projectBloggingBackend.Scripta.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scripta/post")
public class ScriptaPostController {


    private final PostService postService;

    public ScriptaPostController(PostService postService){
      this.postService=postService;
    }
    @GetMapping("/getPosts")
    public List<PostResponseDTO> getAllPost(){
        return postService.getAllPost();
    }

    @PostMapping("/createPost")
    public PostResponseDTO createPost(@Validated @RequestBody PostRequestDTO postRequestDTO){
        return postService.createPost(postRequestDTO);
    }
    @PutMapping("/updatePost/{id}")
    public PostResponseDTO updatePost(@PathVariable long id ,@RequestBody PostRequestDTO postRequestDTO){
        return postService.updatePost(id,postRequestDTO);
    }
    @DeleteMapping("/deletePost/{id}")
    public ResponseEntity<String> deletePost(@PathVariable long id){
        return postService.deletePost(id);
    }
}
