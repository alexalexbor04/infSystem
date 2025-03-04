package org.example.infsystem.services;

import org.example.infsystem.entities.Address;
import org.example.infsystem.repos.AddressRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepo repo;

    public List<Address> listAll() {
        return repo.searchAddress();
    }

    public Address save (Address serv) {
        repo.save(serv);
        return serv;
    }

    public void delete (Long id) {
        repo.deleteById(id);
    }

    public Address get(Long id) {
        return repo.findById(id).get();
    }

    public Address findById(Long id) {
        return repo.findById(id).get();
    }
}
