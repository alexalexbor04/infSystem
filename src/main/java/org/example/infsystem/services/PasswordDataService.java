package org.example.infsystem.services;

import org.example.infsystem.dto.PasswordDataDTO;
import org.example.infsystem.entities.PasswordData;
import org.example.infsystem.repos.PasswordDataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PasswordDataService {

    private final PasswordDataRepo repo;

    @Autowired
    public PasswordDataService(PasswordDataRepo repo) {
        this.repo = repo;
    }

    public List<PasswordDataDTO> listAll() {
        return repo.findAll().stream()
                .map(us -> new PasswordDataDTO(
                        us.getId(),
                        us.getUserId().getUsername(),
                        us.getUser_login(),
                        us.getAddressId().getAddress_name(),
                        us.getAddressId().getUrl(),
                        us.getUser_password(),
                        us.getUserId().getId(),
                        us.getAddressId().getId()))
                .toList();
    }

//    public List<PasswordData> getAll() {
//        return repo.listAllAsList();
//    }

    public PasswordDataDTO getById(Long id) {
        PasswordData pd =  repo.findById(id).
                orElseThrow(() -> new RuntimeException("No PasswordData found with id: " + id));
        return new PasswordDataDTO(
                pd.getId(),
                pd.getUserId().getUsername(),
                pd.getUser_login(),
                pd.getAddressId().getAddress_name(),
                pd.getAddressId().getUrl(),
                pd.getUser_password(),
                pd.getUserId().getId(),
                pd.getAddressId().getId()
        );
    }

    public PasswordData get(Long id) {
        return repo.findById(id).get();
    }

    public PasswordData save (PasswordData user) {
        repo.save(user);
        return user;
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

}
