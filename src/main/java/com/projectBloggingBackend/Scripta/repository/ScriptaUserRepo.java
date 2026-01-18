package com.projectBloggingBackend.Scripta.repository;

import com.projectBloggingBackend.Scripta.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ScriptaUserRepo extends JpaRepository<User,Long> {

    @Query("select u from User u where email = :email")
    User findByEmail(@Param("email") String email);
}