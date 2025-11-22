package com.integrador.tpe.msvcgateway.controller;

import com.integrador.tpe.msvcgateway.dto.request.LoginRequest;
import com.integrador.tpe.msvcgateway.dto.response.LoginResponse;
import com.integrador.tpe.msvcgateway.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.authenticate(request);

        if (response.success()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(401).body(response);
    }
}
