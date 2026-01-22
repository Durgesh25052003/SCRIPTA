package com.projectBloggingBackend.Scripta.repository;

import com.projectBloggingBackend.Scripta.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScriptaCommentRepo extends JpaRepository<Comment,Long> {

}
