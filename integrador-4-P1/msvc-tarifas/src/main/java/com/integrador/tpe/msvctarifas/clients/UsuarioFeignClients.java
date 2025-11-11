package com.integrador.tpe.msvctarifas.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-usuarios", path = "api/usuarios")
public interface UsuarioFeignClients {
    @GetMapping("/admin/{idAdmin}")
    boolean isAdmin(@PathVariable Long idAdmin);
}
