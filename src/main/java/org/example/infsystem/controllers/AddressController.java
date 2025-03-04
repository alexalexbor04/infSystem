package org.example.infsystem.controllers;

import org.example.infsystem.entities.Address;
import org.example.infsystem.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/addresses")
public class AddressController {

    private final AddressService serv;

    @Autowired
    public AddressController(AddressService serv) {
        this.serv = serv;
    }

    @GetMapping
    public ResponseEntity<List<Address>> viewServices() {
        List<Address> listAddr;
        listAddr = serv.listAll();
        return ResponseEntity.ok(listAddr);
    }

    @PostMapping("/new")
    public ResponseEntity<Address> addService(@RequestBody Address service) {
        try {
            Address createdServ = serv.save(service);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdServ);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Address> updateService(@PathVariable Long id, @RequestBody Address service) {
        try {
            Address existServ = serv.get(id);
            if (existServ != null) {
                existServ.setAddress_name(service.getAddress_name());
                existServ.setUrl(service.getUrl());
                existServ.setDescription(service.getDescription());
                Address updatedServ = serv.save(existServ);
                return ResponseEntity.ok(updatedServ);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        try {
            serv.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
