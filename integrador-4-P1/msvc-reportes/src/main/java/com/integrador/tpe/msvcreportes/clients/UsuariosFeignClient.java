package com.integrador.tpe.msvcreportes.clients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "msvc-usuarios", path = "api/usuarios")
public interface UsuariosFeignClient {

}
