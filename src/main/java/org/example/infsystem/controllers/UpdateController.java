package org.example.infsystem.controllers;

import org.example.infsystem.config.JwtAuthenticationFilter;
import org.example.infsystem.entities.Address;
import org.example.infsystem.entities.PasswordData;
import org.example.infsystem.services.AddressService;
import org.example.infsystem.services.PasswordDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UpdateController {

    private final AddressService adServ;
    private final PasswordDataService pwdService;

    @Autowired
    public UpdateController(AddressService adServ, PasswordDataService pwdService) {
        this.adServ = adServ;
        this.pwdService = pwdService;
    }

    @GetMapping("/addresses/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable Long id) {
        try {
            Address address = adServ.get(id);
            if (address != null) {
                return ResponseEntity.ok(address);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/data/{id}")
    public ResponseEntity<PasswordData> getPassById(@PathVariable Long id) {
        try {
            PasswordData data = pwdService.get(id);
            if (data != null) {
                return ResponseEntity.ok(data);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/cur-user")
    public ResponseEntity<Long> someEndpoint(@RequestHeader("Authorization") String token) {
        Long userId = JwtAuthenticationFilter.extractUserId(token.replace("Bearer ", ""));
        return ResponseEntity.ok( userId);
    }
}
