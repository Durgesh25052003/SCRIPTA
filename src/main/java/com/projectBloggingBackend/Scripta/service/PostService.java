package com.projectBloggingBackend.Scripta.service;

import com.projectBloggingBackend.Scripta.dtos.PostRequestDTO;
import com.projectBloggingBackend.Scripta.dtos.PostResponseDTO;
import com.projectBloggingBackend.Scripta.exception.ForbiddenAccess;
import com.projectBloggingBackend.Scripta.exception.UserNotFound;
import com.projectBloggingBackend.Scripta.model.Post;
import com.projectBloggingBackend.Scripta.model.User;
import com.projectBloggingBackend.Scripta.repository.ScriptaPostRepo;
import com.projectBloggingBackend.Scripta.repository.ScriptaUserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final ScriptaUserRepo scriptaUserRepo;
    private final ScriptaPostRepo scriptaPostRepo;

    public PostService(ScriptaUserRepo scriptaUserRepo,
                       ScriptaPostRepo scriptaPostRepo) {
        this.scriptaUserRepo = scriptaUserRepo;
        this.scriptaPostRepo = scriptaPostRepo;
    }

    public PostResponseDTO createPost(PostRequestDTO postRequestDTO) throws RuntimeException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String name = authentication.getName();

        User author = scriptaUserRepo.findByEmail(name);

        if (author == null) {
            throw new UserNotFound("User Not Found");
        }

        //Mapping to Entity
        Post post = new Post();
        post.setContent(postRequestDTO.getContent());
        post.setTitle(postRequestDTO.getTitle());
        post.setAuthor(author);
        post.setCreatedAt(LocalDateTime.now());

        Post postPersisted = scriptaPostRepo.save(post);
        return PostMaptoDTO(postPersisted);
    }

    //Get All Posts
    public List<PostResponseDTO> getAllPost(){
        List<Post>allPosts=scriptaPostRepo.findAll();
        List<PostResponseDTO>allPostsDTO=allPosts.stream().map(this::PostMaptoDTO).toList();
        return allPostsDTO;
    }
    //Update Post
    public PostResponseDTO updatePost(Long id,PostRequestDTO postRequestDTO){
        Post existingPost= scriptaPostRepo.findById(id).orElseThrow(()-> new RuntimeException("Post doesn't Exists..."));
        //Getting author id from posts
        long authorId=existingPost.getAuthorId().getUserID();

        //Getting authenticated User
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        User isUser=scriptaUserRepo.findByEmail(email);

        if(authorId != isUser.getUserID()){
           throw new ForbiddenAccess("Forbidden Access...");
        }

        if(existingPost.getTitle()!=null){
            existingPost.setTitle(postRequestDTO.getTitle());
            existingPost.setUpdatedAt(LocalDateTime.now());
        }
        if(existingPost.getContent()!=null){
            existingPost.setContent(postRequestDTO.getContent());
            existingPost.setUpdatedAt(LocalDateTime.now());
        }
        Post updatedPost= scriptaPostRepo.save(existingPost);
        return PostMaptoDTO(updatedPost);
    }
    //Delete
    public ResponseEntity<String> deletePost(Long id){
        if(!scriptaPostRepo.existsById(id)){
            return new ResponseEntity<>("Post doesn't Exists...", HttpStatus.NOT_FOUND);
        }
        //Getting author id from posts
        Post existingPost= scriptaPostRepo.findById(id).orElseThrow(()-> new RuntimeException("Post doesn't Exists..."));
        long authorId=existingPost.getAuthorId().getUserID();

        //Getting authenticated User
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        User isUser=scriptaUserRepo.findByEmail(email);

        if(authorId != isUser.getUserID()){
            throw new ForbiddenAccess("Forbidden Access...");
        }
        scriptaPostRepo.deleteById(id);
        return new ResponseEntity<>("Post Deleted Successfully",HttpStatus.OK);
    }

    public PostResponseDTO PostMaptoDTO(Post postBody) {
        PostResponseDTO postResponseDTO = new PostResponseDTO();
        postResponseDTO.setTitle(postBody.getTitle());
        postResponseDTO.setId(postBody.getPostId());
        postResponseDTO.setContent(postBody.getContent());
        postResponseDTO.setCreatedAt(postBody.getCreatedAt());
        postResponseDTO.setAuthorUsername(postBody.getAuthorId().getEmail());
        System.out.println(postResponseDTO);
        return postResponseDTO;
    }
}
