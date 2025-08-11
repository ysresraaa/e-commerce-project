package com.esrayasar.e_commerce.project.controller;


import com.esrayasar.e_commerce.project.dto.AuthenticationRequest;
import com.esrayasar.e_commerce.project.dto.AuthenticationResponse;
import com.esrayasar.e_commerce.project.dto.RegisterRequest;
import com.esrayasar.e_commerce.project.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request
    ) {

        return ResponseEntity.ok(authenticationService.login(request));
    }

    // Dosyanın en altına, login metodundan sonra ekle

    @GetMapping("/demo")
    public ResponseEntity<String> demo() {
        return ResponseEntity.ok("Hello,You entered with token !");
    }
}