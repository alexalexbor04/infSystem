package org.example.infsystem.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.infsystem.dto.PasswordDataDTO;
import org.example.infsystem.entities.Address;
import org.example.infsystem.entities.PasswordData;
import org.example.infsystem.repos.AddressRepo;
import org.example.infsystem.services.PasswordDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/data")
public class PDController {

    private final PasswordDataService serv;
    private final AddressRepo adRepo;

    @Autowired
    public PDController(PasswordDataService serv, AddressRepo adRepo) {
        this.serv = serv;
        this.adRepo = adRepo;
    }

    @GetMapping
    public ResponseEntity<List<PasswordDataDTO>> viewUsServ () {
        List<PasswordDataDTO> listPD;
            listPD = serv.listAll();
        return ResponseEntity.ok(listPD);
    }

    @PostMapping("/new")
    public ResponseEntity<PasswordData> addUsServ(@RequestBody PasswordData us) throws JsonProcessingException {
        System.out.println("Полученные данные:");
        System.out.println("Login: " + us.getUser_login());
        System.out.println("Password: " + us.getUser_password());
        System.out.println("User ID: " + us.getUserId());
        System.out.println("Address ID: " + us.getAddressId());
        System.out.println(new ObjectMapper().writeValueAsString(us));

        try {
            Address address = adRepo.findById(us.getAddressId().getId())
                    .orElseThrow(() -> new RuntimeException("Address not found with id: " + us.getAddressId()));

            us.setAddressId(address);

            PasswordData newUS = serv.save(us);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUS);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<PasswordData> updateUsServ(@PathVariable Long id, @RequestBody PasswordData updatedData) {
        try {
            // Получаем текущую запись из базы
            PasswordData existingUS = serv.get(id);
            if (existingUS != null) {
                // Обновляем только те поля, которые должны меняться
                existingUS.setUser_login(updatedData.getUser_login());
                existingUS.setUser_password(updatedData.getUser_password());

                // Обновляем address_id, если он передан
                if (updatedData.getAddressId() != null) {
                    existingUS.setAddressId(updatedData.getAddressId());
                }

                // Сохраняем изменения
                PasswordData savedUS = serv.save(existingUS);
                return ResponseEntity.ok(savedUS);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsServ (@PathVariable Long id) {
        try {
            serv.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

