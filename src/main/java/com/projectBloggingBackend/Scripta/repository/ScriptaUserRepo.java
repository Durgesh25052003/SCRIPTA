package com.projectBloggingBackend.Scripta.repository;

import com.projectBloggingBackend.Scripta.model.Role;
import com.projectBloggingBackend.Scripta.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ScriptaUserRepo extends JpaRepository<User,Long> {
}