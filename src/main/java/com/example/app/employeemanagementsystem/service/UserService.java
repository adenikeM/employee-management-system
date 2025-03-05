package com.example.app.employeemanagementsystem.service;

import com.example.app.employeemanagementsystem.model.User;
import com.example.app.employeemanagementsystem.model.dto.JwtAuthenticationResponse;
import com.example.app.employeemanagementsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }


    public User createUser(User user) {
        user.setPassword(encoder.encode(user.getPassword())); // Ensure password is encoded
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id)
                                          .orElseThrow(() -> new RuntimeException("User not found with id " + id));

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setUserName(user.getUserName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(encoder.encode(user.getPassword()));
        existingUser.setRole(user.getRole());

        return userRepository.save(existingUser);
    }
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
            }
        };
    }

    public User save (User newUser){
        return userRepository.save(newUser);
    }

}