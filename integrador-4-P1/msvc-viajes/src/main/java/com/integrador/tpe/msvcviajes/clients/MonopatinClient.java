package com.integrador.tpe.msvcviajes.clients;

import com.integrador.tpe.msvcviajes.dto.interservice.Monopatin;
import com.integrador.tpe.msvcviajes.dto.interservice.ViajeResumen;
import org.hibernate.mapping.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "msvc-flota", path = "/monopatines")
public class MonopatinClient {

}
