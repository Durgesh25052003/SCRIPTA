package com.projectBloggingBackend.Scripta.service;

import com.projectBloggingBackend.Scripta.dtos.PostResponseDTO;
import com.projectBloggingBackend.Scripta.exception.UserNotFound;
import com.projectBloggingBackend.Scripta.model.Follow;
import com.projectBloggingBackend.Scripta.model.Post;
import com.projectBloggingBackend.Scripta.model.User;
import com.projectBloggingBackend.Scripta.repository.ScriptaFollowRepo;
import com.projectBloggingBackend.Scripta.repository.ScriptaPostRepo;
import com.projectBloggingBackend.Scripta.repository.ScriptaUserRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedService {

    private final ScriptaFollowRepo scriptaFollowRepo;
    private final ScriptaUserRepo scriptaUserRepo;
    private final  PostService postService;
    private final ScriptaPostRepo scriptaPostRepo;

    public FeedService(ScriptaFollowRepo scriptaFollowRepo,ScriptaUserRepo scriptaUserRepo,ScriptaPostRepo scriptaPostRepo,PostService postService){
        this.scriptaFollowRepo=scriptaFollowRepo;
        this.scriptaUserRepo=scriptaUserRepo;
        this.scriptaPostRepo=scriptaPostRepo;
        this.postService=postService;
    }

    public List<PostResponseDTO> feed(){
        try {
            String email= SecurityContextHolder.getContext().getAuthentication().getName();
            User userAutheticated=scriptaUserRepo.findByEmail(email).orElseThrow(()-> new UserNotFound("User Dosent Exists..."));
            List<User> following=scriptaFollowRepo.findByFollower(userAutheticated).stream().map(Follow::getFollowing).toList();
            List<Post> postsOg= scriptaPostRepo.findByAuthorIdInOrderByCreatedAtDesc(following);
            List<PostResponseDTO> postResponseDTOS= postsOg.stream().map(postService::PostMaptoDTO).toList();
            return postResponseDTOS;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
