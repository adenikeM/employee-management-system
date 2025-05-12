package com.example.app.employeemanagementsystem.controller;

import com.example.app.employeemanagementsystem.model.User;
import com.example.app.employeemanagementsystem.model.dto.ErrorResponse;
import com.example.app.employeemanagementsystem.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        log.info("Get User by ID {} ", id);
        if (id < 1) {
            return ResponseEntity.badRequest().body(
                    ErrorResponse.buildErrorResponse(HttpStatus.BAD_REQUEST.value(),
                            "ID cannot be less than 1",
                            "Invalid ID")
            );
        }
        return userService.getUserById(id)
                          .map(leaveRequest -> ResponseEntity.ok().body(leaveRequest))
                          .orElseThrow(() -> new RuntimeException("LeaveRequest not found"));
    }
    @PostMapping("users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = userService.createUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(id, user);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(ErrorResponse.buildErrorResponse(HttpStatus.NOT_FOUND.value(),
                                         "User Not Found",
                                         "User with ID " + id + " not found for update"));
        }
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUserById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(ErrorResponse.buildErrorResponse(HttpStatus.NOT_FOUND.value(),
                                         "User Not Found",
                                         "User with ID " + id + " not found for deletion"));
        }
    }
}
