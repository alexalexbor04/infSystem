package org.example.infsystem.repos;

import org.example.infsystem.dto.PasswordDataDTO;
import org.example.infsystem.entities.PasswordData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PasswordDataRepo extends JpaRepository<PasswordData, Long> {

    @Query("select new org.example.infsystem.dto.PasswordDataDTO(pd.id, u.username, pd.user_login, a.address_name, " +
            "a.url, pd.user_password, u.id, a.id) " +
            "from PasswordData pd " +
            "join pd.userId u " +
            "join pd.address_id a")
    List<PasswordDataDTO> listAll();

    @Query("select p from PasswordData p")
    List<PasswordData> listAllAsList();

}
