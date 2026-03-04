package com.projectBloggingBackend.Scripta.repository;

import com.projectBloggingBackend.Scripta.model.Follow;
import com.projectBloggingBackend.Scripta.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScriptaFollowRepo extends JpaRepository<Follow,Long> {

    boolean existsByFollowerAndFollowing(User Follower , User Following);

    void deleteByFollowerAndFollowing(User Follower ,User Following);
    //Give whom you follow
    List<Follow> findByFollower(User Follower);

    //Give who follows You
    List<Follow> findByFollowing(User Follower);
}
