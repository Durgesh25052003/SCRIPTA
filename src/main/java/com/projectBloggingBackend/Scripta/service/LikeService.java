package com.projectBloggingBackend.Scripta.service;

import com.projectBloggingBackend.Scripta.exception.ResourceNotFound;
import com.projectBloggingBackend.Scripta.exception.UserNotFound;
import com.projectBloggingBackend.Scripta.model.Like;
import com.projectBloggingBackend.Scripta.model.Post;
import com.projectBloggingBackend.Scripta.model.User;
import com.projectBloggingBackend.Scripta.repository.ScriptaLikeRepo;
import com.projectBloggingBackend.Scripta.repository.ScriptaPostRepo;
import com.projectBloggingBackend.Scripta.repository.ScriptaUserRepo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
    private final ScriptaLikeRepo scriptaLikeRepo;
    private final ScriptaPostRepo scriptaPostRepo;
    private final ScriptaUserRepo scriptaUserRepo;

    public LikeService(ScriptaLikeRepo scriptaLikeRepo,ScriptaUserRepo scriptaUserRepo,ScriptaPostRepo scriptaPostRepo){
        this.scriptaLikeRepo=scriptaLikeRepo;
        this.scriptaPostRepo=scriptaPostRepo;
        this.scriptaUserRepo=scriptaUserRepo;
    }

    public void likePost(long postId){
        Authentication autheticatedUser= SecurityContextHolder.getContext().getAuthentication();
        String email=autheticatedUser.getName();
        User user= scriptaUserRepo.findByEmail(email).orElseThrow(()-> new UserNotFound("User not Found..."));

        Post post=scriptaPostRepo.findById(postId).orElseThrow(()-> new UserNotFound("User not Found..."));

        Boolean alreadyLike = scriptaLikeRepo.findByUserAndPost(user.getUserID(), post.getPostId()).isPresent();
        if(alreadyLike){
            return;
        }

        Like like= new Like();
        like.setPost_id(post);
        like.setUser_id(user);

        Like persistedLike=scriptaLikeRepo.save(like);
    }

    public void unlikePost(Long postId) {

        User user = getAuthenticatedUser();

        Post post = scriptaPostRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFound("Post not found"));

        scriptaLikeRepo.findByUserAndPost(user.getUserID(), post.getPostId())
                .ifPresent(scriptaLikeRepo::delete);
    }
    private User getAuthenticatedUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String identity = authentication.getName(); // email or username

        return scriptaUserRepo.findByEmail(identity)
                .orElseThrow(() -> new UserNotFound("User not found"));
    }
}
