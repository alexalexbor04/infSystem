package org.example.infsystem.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AboutController {

    @GetMapping("/about")
    public ResponseEntity<Void> aboutPage() {
        System.out.println("about page передается...");
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
