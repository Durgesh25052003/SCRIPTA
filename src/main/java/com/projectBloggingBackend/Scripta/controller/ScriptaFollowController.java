package com.projectBloggingBackend.Scripta.controller;

import com.projectBloggingBackend.Scripta.dtos.FollowRequestDTO;
import com.projectBloggingBackend.Scripta.repository.ScriptaFollowRepo;
import com.projectBloggingBackend.Scripta.repository.ScriptaLikeRepo;
import com.projectBloggingBackend.Scripta.service.FollowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/scripta/follow")
public class ScriptaFollowController {
       private final FollowService followService;

       public ScriptaFollowController(FollowService followService){
           this.followService=followService;
       }

       @PostMapping("/f")
       public ResponseEntity<String> Follow(@RequestBody FollowRequestDTO followerIdBody){
           Long followerId= followerIdBody.getFollowingId();
           return followService.follow(followerId);
       }
       @DeleteMapping("/unFollow")
       public ResponseEntity<String> unFollow(@RequestBody FollowRequestDTO followerIdBody){
        Long followerId= followerIdBody.getFollowingId();
        System.out.println("✨✨✨💙💙");
        return followService.unFollow(followerId);
    }
}
