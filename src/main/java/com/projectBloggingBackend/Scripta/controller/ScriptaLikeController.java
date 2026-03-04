package com.projectBloggingBackend.Scripta.controller;

import com.projectBloggingBackend.Scripta.service.LikeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/scripta/like")
public class ScriptaLikeController {
    private final LikeService likeService;

    public ScriptaLikeController(LikeService likeService){
        this.likeService=likeService;
    }

    @PostMapping("/likePost/{postId}")
    @PreAuthorize("isAuthenticated()")
    public void likePost(@PathVariable long postId){
        likeService.likePost(postId);
    }
    @DeleteMapping("/unLikePost/{postId}")
    @PreAuthorize("isAuthenticated()")
    public void unLikePost(@PathVariable long postId){
        likeService.unlikePost(postId);
    }
}
