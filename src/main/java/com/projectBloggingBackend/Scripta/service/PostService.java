package com.projectBloggingBackend.Scripta.service;

import com.projectBloggingBackend.Scripta.dtos.PostRequestDTO;
import com.projectBloggingBackend.Scripta.dtos.PostResponseDTO;
import com.projectBloggingBackend.Scripta.exception.ForbiddenAccess;
import com.projectBloggingBackend.Scripta.exception.ResourceNotFound;
import com.projectBloggingBackend.Scripta.exception.UserNotFound;
import com.projectBloggingBackend.Scripta.model.Post;
import com.projectBloggingBackend.Scripta.model.User;
import com.projectBloggingBackend.Scripta.repository.ScriptaPostRepo;
import com.projectBloggingBackend.Scripta.repository.ScriptaUserRepo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PostService {
    private final ScriptaUserRepo scriptaUserRepo;
    private final ScriptaPostRepo scriptaPostRepo;
    private Logger logger=LoggerFactory.getLogger(PostService.class);
    public PostService(ScriptaUserRepo scriptaUserRepo,
                       ScriptaPostRepo scriptaPostRepo) {
        this.scriptaUserRepo = scriptaUserRepo;
        this.scriptaPostRepo = scriptaPostRepo;
    }

    public ResponseEntity<PostResponseDTO> createPost(PostRequestDTO postRequestDTO) throws RuntimeException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String name = authentication.getName();

        User author = scriptaUserRepo.findByEmail(name).orElseThrow(()-> new UserNotFound("User not found..."));


        //Mapping to Entity
        Post post = new Post();
        post.setContent(postRequestDTO.getContent());
        post.setTitle(postRequestDTO.getTitle());
        post.setAuthor(author);
        post.setCreatedAt(LocalDateTime.now());

        Post postPersisted = scriptaPostRepo.save(post);
        return new ResponseEntity<>(PostMaptoDTO(postPersisted), HttpStatus.CREATED);
    }

    //Get All Posts
    public List<PostResponseDTO> getAllPost(){
        List<Post>allPosts=scriptaPostRepo.findAll();
        List<PostResponseDTO>allPostsDTO=allPosts.stream().map(this::PostMaptoDTO).toList();
        return allPostsDTO;
    }

    //GetPost By Id
    @Cacheable(value = "posts", key = "#id")
     public PostResponseDTO getPostById(Long id){
        logger.info("FETCHING FROM DATABASES...............");
        Post post=scriptaPostRepo.findById(id).orElseThrow(()-> new ResourceNotFound("Post Doesnt Exists"));
        return PostMaptoDTO(post);
     }
    //Update Post
    @CacheEvict(value = "posts", key = "#id")
    public PostResponseDTO updatePost(Long id,PostRequestDTO postRequestDTO){
        Post existingPost= scriptaPostRepo.findById(id).orElseThrow(()-> new RuntimeException("Post doesn't Exists..."));
        //Getting author id from posts
        long authorId=existingPost.getAuthorId().getUserID();

        //Getting authenticated User
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        User isUser=scriptaUserRepo.findByEmail(email).orElseThrow(()-> new UserNotFound("User not found..."));

        if(authorId != isUser.getUserID()){
           throw new ForbiddenAccess("Forbidden Access...");
        }
        System.out.println("😉😉😉");
        if(postRequestDTO.getTitle()!=null){
            System.out.println("😉😉😉");
            existingPost.setTitle(postRequestDTO.getTitle());
            existingPost.setUpdatedAt(LocalDateTime.now());
        }
        if(postRequestDTO.getContent()!=null){
            existingPost.setContent(postRequestDTO.getContent());
            existingPost.setUpdatedAt(LocalDateTime.now());
        }
        Post updatedPost= scriptaPostRepo.save(existingPost);
        return PostMaptoDTO(updatedPost);
    }
    //Delete
    @CacheEvict(value = "posts", key = "#id")
    public ResponseEntity<String> deletePost(Long id){
        if(!scriptaPostRepo.existsById(id)){
            return new ResponseEntity<>("Post doesn't Exists...", HttpStatus.NOT_FOUND);
        }
        //Getting author id from posts
        Post existingPost= scriptaPostRepo.findById(id).orElseThrow(()-> new RuntimeException("Post doesn't Exists..."));
        long authorId=existingPost.getAuthorId().getUserID();

        //Getting authenticated User
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        User isUser=scriptaUserRepo.findByEmail(email).orElseThrow(()-> new UserNotFound("User not found..."));

        if(authorId != isUser.getUserID()){
            throw new ForbiddenAccess("Forbidden Access...");
        }
        scriptaPostRepo.deleteById(id);
        return new ResponseEntity<>("Post Deleted Successfully",HttpStatus.OK);
    }

    public PostResponseDTO PostMaptoDTO(Post postBody) {
        PostResponseDTO postResponseDTO = new PostResponseDTO();
        postResponseDTO.setTitle(postBody.getTitle());
        postResponseDTO.setPostId(postBody.getPostId());
        postResponseDTO.setContent(postBody.getContent());
        postResponseDTO.setCreatedAt(postBody.getCreatedAt());
        postResponseDTO.setAuthorUsername(postBody.getAuthorId().getEmail());
        System.out.println(postResponseDTO);
        return postResponseDTO;
    }
}
