package com.integrador.tpe.msvcviajes.clients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "msvc-flota", path = "/monopatines")
public interface MonopatinFeignCLient {
    boolean verificarDisponibilidadMonopatin(Long idMonopatin);
}
