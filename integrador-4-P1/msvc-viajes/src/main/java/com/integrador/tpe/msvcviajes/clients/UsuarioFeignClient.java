package com.integrador.tpe.msvcviajes.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-usuarios", contextId = "usuariosId", path = "api/usuarios")
public interface UsuarioFeignClient {
    @GetMapping("/{id}/exists")
    boolean existsUsuarioById(@PathVariable Long id);

    @GetMapping("/{idUsuario}/cuenta/{idCuenta}")
    boolean estaAsociadoConCuenta(@PathVariable Long idUsuario, @PathVariable Long idCuenta);
}
