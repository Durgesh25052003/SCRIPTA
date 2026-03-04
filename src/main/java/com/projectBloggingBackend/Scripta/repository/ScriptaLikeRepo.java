package com.projectBloggingBackend.Scripta.repository;

import com.projectBloggingBackend.Scripta.model.Like;
import com.projectBloggingBackend.Scripta.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScriptaLikeRepo extends JpaRepository<Like,Long> {
    Optional<Like> findByUserAndPost(long user, long post);

    long countByPost(Post post);
}
