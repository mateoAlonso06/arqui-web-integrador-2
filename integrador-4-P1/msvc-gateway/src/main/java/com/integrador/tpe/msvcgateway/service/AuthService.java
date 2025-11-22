package com.integrador.tpe.msvcgateway.service;

import com.integrador.tpe.msvcgateway.client.UsuarioFeignClient;
import com.integrador.tpe.msvcgateway.config.JwtUtil;
import com.integrador.tpe.msvcgateway.dto.UsuarioResponseDTO;
import com.integrador.tpe.msvcgateway.dto.request.LoginRequest;
import com.integrador.tpe.msvcgateway.dto.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtil jwtUtil;
    private final UsuarioFeignClient usuarioFeignClient;

    public LoginResponse authenticate(LoginRequest request) {
        ResponseEntity<UsuarioResponseDTO> response = usuarioFeignClient.validateCredentials(request);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            UsuarioResponseDTO usuario = response.getBody();
            String token = jwtUtil.generateToken(usuario.email(), usuario.role().toString());

            return new LoginResponse(
                    token,
                    usuario.email(),
                    usuario.role(),
                    true,
                    null
            );
        }

        return new LoginResponse(null, null, null, false, "Credenciales inválidas");
    }
}
