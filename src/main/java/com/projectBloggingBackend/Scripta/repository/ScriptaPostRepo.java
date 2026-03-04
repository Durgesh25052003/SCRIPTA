package com.projectBloggingBackend.Scripta.repository;

import com.projectBloggingBackend.Scripta.model.Post;
import com.projectBloggingBackend.Scripta.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScriptaPostRepo extends JpaRepository<Post, Long> {
   List<Post> findByAuthorIdInOrderByCreatedAtDesc(List<User>following);
}
