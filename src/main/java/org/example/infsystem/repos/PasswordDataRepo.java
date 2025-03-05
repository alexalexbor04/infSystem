package org.example.infsystem.repos;

import org.example.infsystem.entities.PasswordData;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PasswordDataRepo extends JpaRepository<PasswordData, Long> {

}
