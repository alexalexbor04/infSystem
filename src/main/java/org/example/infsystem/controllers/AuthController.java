package org.example.infsystem.controllers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpSession;
import org.example.infsystem.entities.Roles;
import org.example.infsystem.entities.User;
import org.example.infsystem.repos.RoleRepo;
import org.example.infsystem.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userRepo.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Имя пользователя уже существует"));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Roles userRole = roleRepo.findByName("user")
                .orElseThrow(() -> new RuntimeException("Роль user не найдена"));
        user.setRole(userRole);

        userRepo.save(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "Регистрация прошла успешно!"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        User existingUser = userRepo.findByUsername(user.getUsername())
                .orElse(null);

        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Пользователь не найден"));
        }

        if (passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            String token = Jwts.builder()
                    .setSubject(existingUser.getUsername())
                    .claim("id", existingUser.getId())
                    .claim("roles", existingUser.getRole())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 час
                    .signWith(SignatureAlgorithm.HS512, "secretKeySecretKey12345678secretKeySecretKey12345678" +
                            "secretKeySecretKey12345678secretKeySecretKey12345678secretKeySecretKey12345678")
                    .compact();


            return ResponseEntity.ok(Map.of("token", token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Неверный пароль"));
        }
    }

    @GetMapping("/login")
    public ResponseEntity<Void> loginPage() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
