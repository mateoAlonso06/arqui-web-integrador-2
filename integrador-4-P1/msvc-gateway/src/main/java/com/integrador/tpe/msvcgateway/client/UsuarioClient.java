package com.integrador.tpe.msvcgateway.client;

import com.integrador.tpe.msvcgateway.dto.LoginRequest;
import com.integrador.tpe.msvcgateway.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "msvc-usuario", path = "/api/usuarios")
public interface UsuarioClient {
    @PostMapping("/validate")
    ResponseEntity<UsuarioDTO> validateCredentials(@RequestBody LoginRequest loginRequest);
}
