package org.example.infsystem.services;

import org.example.infsystem.repos.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServicesDet implements UserDetailsService {

    private final UserRepo repo;

    public UserServicesDet(UserRepo userRepository){
        this.repo = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден " + username));
    }

//    public UserDTO convertToDTO(User user) {
//        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole(), user.getFull_name());
//    }
//
//    public List<UserDTO> getAllUsers() {
//        return repo.findAll().stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//
//    }
}

