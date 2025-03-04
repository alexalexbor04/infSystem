package org.example.infsystem.config;

import org.example.infsystem.entities.Roles;
import org.example.infsystem.entities.User;
import org.example.infsystem.repos.RoleRepo;
import org.example.infsystem.repos.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class RoleSetterConf implements CommandLineRunner {

    private final RoleRepo repoRole;
    private final UserRepo repoUser;
    private final PasswordEncoder passEnc;

    public RoleSetterConf(RoleRepo repoRole, UserRepo repoUser, PasswordEncoder passEnc) {
        this.repoRole = repoRole;
        this.repoUser = repoUser;
        this.passEnc = passEnc;
    }

    @Override
    public void run(String... args) throws Exception {
        Roles admRole = repoRole.findByName("admin").orElseGet(() -> {
            Roles role = new Roles();
            role.setName("admin");
            return repoRole.save(role);
        });

        Roles userRole = repoRole.findByName("user").orElseGet(() -> {
            Roles role = new Roles();
            role.setName("user");
            return repoRole.save(role);
        });

        if (!repoUser.findByUsername("adminDeya").isPresent()) {
            User admin = new User();
            admin.setUsername("adminDeya");
            admin.setEmail("admin@email.com");
            admin.setFull_name("aaa");
            admin.setPassword(passEnc.encode("1234"));
            admin.setRole(admRole);
            repoUser.save(admin);
        }

    }
}
