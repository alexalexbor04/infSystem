package org.example.infsystem.services;

import org.example.infsystem.dto.PasswordDataDTO;
import org.example.infsystem.entities.PasswordData;
import org.example.infsystem.repos.PasswordDataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PasswordDataService {

    @Autowired
    private PasswordDataRepo repo;

    public List<PasswordDataDTO> listAll() {
        return repo.findAll().stream()
                .map(us -> new PasswordDataDTO(
                        us.getId(),
                        us.getUserId().getUsername(),
                        us.getUser_login(),
                        us.getAddressId().getAddress_name(),
                        us.getAddressId().getUrl(),
                        us.getUser_password()))
                .toList();
    }

    public List<PasswordData> getAll() {
        return repo.listAllAsList();
    }

    public PasswordData save (PasswordData user) {
        repo.save(user);
        return user;
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public PasswordData get(Long id) {
        return repo.findById(id).get();
    }
}
