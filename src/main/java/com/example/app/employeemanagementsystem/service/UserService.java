package com.example.app.employeemanagementsystem.service;

import com.example.app.employeemanagementsystem.model.User;
import com.example.app.employeemanagementsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsers(){return userRepository.findAll();}

    public Optional<User>getUserById(Long id){return userRepository.findById(id);}

    public User createUser(User user){
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user){
        User existingUser = userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("User not found with id " + id));
        existingUser.setUserName(existingUser.getUserName());
        existingUser.setPassword(existingUser.getPassword());
        existingUser.setRole(existingUser.getRole());

        return userRepository.save(existingUser);
    }

    public void deleteUserById(Long id){userRepository.deleteById(id);}
}