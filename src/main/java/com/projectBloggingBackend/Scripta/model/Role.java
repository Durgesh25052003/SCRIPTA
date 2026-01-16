package com.projectBloggingBackend.Scripta.model;

import jakarta.persistence.*;

@Entity
@Table(name="roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    private String roleName;

    public String getRoleName() {
        return roleName;
    }
}
