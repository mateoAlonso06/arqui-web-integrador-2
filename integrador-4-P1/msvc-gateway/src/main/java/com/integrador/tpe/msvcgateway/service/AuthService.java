package com.integrador.tpe.msvcgateway.service;

import com.integrador.tpe.msvcgateway.client.UsuarioClient;
import com.integrador.tpe.msvcgateway.config.JwtUtil;
import com.integrador.tpe.msvcgateway.dto.LoginRequest;
import com.integrador.tpe.msvcgateway.dto.UsuarioDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtil jwtUtil;
    private final UsuarioClient usuarioClient;

    public LoginResponse authenticate(LoginRequest request) {
        try {
            ResponseEntity<UsuarioDTO> response = usuarioClient.validateCredentials(request);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                UsuarioDTO usuario = response.getBody();
                String token = jwtUtil.generateToken(usuario.username(), usuario.role());

                return new LoginResponse(
                        token,
                        usuario.username(),
                        usuario.role(),
                        true,
                        null
                );
            }

            return new LoginResponse(null, null, null, false, "Credenciales inválidas");

        } catch (Exception e) {
            return new LoginResponse(null, null, null, false, "Error de autenticación");
        }
    }
}
