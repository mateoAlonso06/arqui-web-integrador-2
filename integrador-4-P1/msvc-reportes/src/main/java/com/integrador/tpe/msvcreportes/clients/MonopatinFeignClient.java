package com.integrador.tpe.msvcreportes.clients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "msvc-flota", path = "api/monopatines")
public interface MonopatinFeignClient {
}
