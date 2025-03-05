package org.example.infsystem.controllers;

import org.example.infsystem.entities.Roles;
import org.example.infsystem.entities.User;
import org.example.infsystem.repos.RoleRepo;
import org.example.infsystem.repos.UserRepo;
import org.example.infsystem.services.UserServicesDet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class UserController {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final UserServicesDet serv;

    @Autowired
    public UserController(UserRepo userRepo, RoleRepo roleRepo, UserServicesDet serv) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.serv = serv;
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/users")
    public ResponseEntity<List<User>> listUsers() {
        List<User> users = userRepo.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('admin')")
    @PutMapping("/users/{id}/change-role")
    public ResponseEntity<String> changeRole(@PathVariable("id") Long id, @RequestBody Map<String, String> body) {

        String roleName = body.get("role");
        User user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Неправильный id"));

        Roles role = roleRepo.findByName(roleName)
                .orElseThrow(() -> new IllegalArgumentException("Неверная роль"));
        user.setRole(role);
        userRepo.save(user);

        return ResponseEntity.ok("Роль пользователя изменена");
    }

    @PreAuthorize("hasRole('admin')")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с ID " + id + " не найден"));
        userRepo.delete(user);

        return ResponseEntity.ok("Пользователь с ID " + id + " удалён");
    }
}
