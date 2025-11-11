package com.integrador.tpe.msvcreportes.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-usuarios", path = "api/usuarios")
public interface UsuarioFeignClient {
    @GetMapping("/admin/{idAdmin}")
    boolean esAdministrador(@PathVariable Long idAdmin);
}
