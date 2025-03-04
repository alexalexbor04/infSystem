package org.example.infsystem.dto;

import org.example.infsystem.entities.Roles;

public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private Roles role;
    private String full_name;

    public UserDTO(Long id, String username, String email, Roles role, String full_name) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.full_name = full_name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }
}
