package com.example.app.employeemanagementsystem.controller;

import com.example.app.employeemanagementsystem.model.dto.JwtAuthenticationResponse;
import com.example.app.employeemanagementsystem.model.dto.SignInRequest;
import com.example.app.employeemanagementsystem.model.dto.SignUpRequest;
import com.example.app.employeemanagementsystem.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public JwtAuthenticationResponse signUp(@Valid @RequestBody SignUpRequest request){
        log.info("login request {}", request);
        return authenticationService.signup(request);
    }

    @PostMapping("/signin")
    public JwtAuthenticationResponse signIn(@RequestBody SignInRequest request){
        return authenticationService.signIn(request);
    }
}
