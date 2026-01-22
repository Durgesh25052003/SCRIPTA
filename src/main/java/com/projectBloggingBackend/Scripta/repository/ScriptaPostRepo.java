package com.projectBloggingBackend.Scripta.repository;

import com.projectBloggingBackend.Scripta.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScriptaPostRepo extends JpaRepository<Post, Long> {

}
