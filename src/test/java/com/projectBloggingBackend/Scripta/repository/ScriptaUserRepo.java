package com.projectBloggingBackend.Scripta.repository;

import com.projectBloggingBackend.Scripta.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScriptaUserRepo extends JpaRepository<User,Long> {
}