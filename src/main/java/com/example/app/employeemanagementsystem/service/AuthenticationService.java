package com.example.app.employeemanagementsystem.service;

import com.example.app.employeemanagementsystem.model.User;
import com.example.app.employeemanagementsystem.model.dto.JwtAuthenticationResponse;
import com.example.app.employeemanagementsystem.model.dto.SignInRequest;
import com.example.app.employeemanagementsystem.model.dto.SignUpRequest;
import com.example.app.employeemanagementsystem.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserService userService;

    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserService userService, UserRepository userRepository, PasswordEncoder encoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.encoder = encoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    /*public JwtAuthenticationResponse signup(SignUpRequest request) {
        if (userService.findUserByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already in use");
        }
        *//*User newUser = new User();
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setUserName(request.getUserName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole(request.getRole());

        return userRepository.save(newUser);*//*

        var user = User
                .builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        user = userService.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();

    }*/

    public JwtAuthenticationResponse signup(SignUpRequest request) {
        if (userService.findUserByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already in use");
        }

        User user = new User(
                null,  // ID will be auto-generated
                request.getFirstName(),
                request.getLastName(),
                request.getUserName(),
                request.getEmail(),
                encoder.encode(request.getPassword()),
                request.getRole()
        );

        user = userService.save(user);
        String jwt = jwtService.generateToken(user);

        return new JwtAuthenticationResponse(jwt);
    }

    public JwtAuthenticationResponse signIn(SignInRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userService.findUserByEmail(request.getEmail())
                              .orElseThrow(() -> new IllegalArgumentException("Invalid Email or Password"));
        var jwt = jwtService.generateToken(user);
        return  new JwtAuthenticationResponse(jwt);
    }
}
