package com.integrador.tpe.msvcgateway.client;

import com.integrador.tpe.msvcgateway.dto.UsuarioResponseDTO;
import com.integrador.tpe.msvcgateway.dto.request.LoginRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "msvc-usuarios", path = "/api/usuarios")
public interface UsuarioFeignClient {

    @PostMapping("/validate")
    ResponseEntity<UsuarioResponseDTO> validateCredentials(@RequestBody LoginRequest loginRequest);
}

