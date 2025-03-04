package org.example.infsystem.repos;

import org.example.infsystem.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepo extends JpaRepository<Address, Long> {

    @Query("select s from Address s")
    List<Address> searchAddress();
}
