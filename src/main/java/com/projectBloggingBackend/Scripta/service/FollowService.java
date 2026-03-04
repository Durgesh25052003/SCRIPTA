package com.projectBloggingBackend.Scripta.service;

import com.projectBloggingBackend.Scripta.exception.UserNotFound;
import com.projectBloggingBackend.Scripta.model.Follow;
import com.projectBloggingBackend.Scripta.model.User;
import com.projectBloggingBackend.Scripta.repository.ScriptaFollowRepo;
import com.projectBloggingBackend.Scripta.repository.ScriptaUserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class FollowService {
    private final ScriptaFollowRepo scriptaFollowRepo;
    private final ScriptaUserRepo scriptaUserRepo;

    public FollowService(ScriptaFollowRepo scriptaFollowRepo,ScriptaUserRepo scriptaUserRepo){
        this.scriptaFollowRepo=scriptaFollowRepo;
        this.scriptaUserRepo=scriptaUserRepo;
    }
    public ResponseEntity<String> follow(Long userId){
        String getName= SecurityContextHolder.getContext().getAuthentication().getName();
        User userFollower = scriptaUserRepo.findByEmail(getName).orElseThrow(()-> new UserNotFound("User not exits"));
        User userFollowing = scriptaUserRepo.findById(userId).orElseThrow(()-> new UserNotFound("User not exits"));

        if(scriptaFollowRepo.existsByFollowerAndFollowing(userFollower,userFollowing))
        return new ResponseEntity<>("You Follow This User Already",HttpStatus.BAD_REQUEST);

        Follow follow = new Follow();

        follow.setFollowerId(userFollower);
        follow.setFollowingId(userFollowing);
        scriptaFollowRepo.save(follow);
        String message = "You Followed"+ userFollowing.getUserName();
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }
    @Transactional
    public ResponseEntity<String>unFollow(Long userId){
        String getName= SecurityContextHolder.getContext().getAuthentication().getName();
        User userFollower = scriptaUserRepo.findByEmail(getName).orElseThrow(()-> new UserNotFound("User not exits"));
        User userFollowing = scriptaUserRepo.findById(userId).orElseThrow(()-> new UserNotFound("User not exits"));
        System.out.println("✨✨✨");
        scriptaFollowRepo.deleteByFollowerAndFollowing(userFollower,userFollowing);
        String message = "You UnFollowed"+ userFollowing.getUserName();
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }
}
