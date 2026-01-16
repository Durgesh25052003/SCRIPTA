package com.projectBloggingBackend.Scripta.repository;

import com.projectBloggingBackend.Scripta.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ScriptaRoleRepo extends JpaRepository<Role,Long> {
    Set<Role>findByRoleNameIn(Set<String>role);
}
