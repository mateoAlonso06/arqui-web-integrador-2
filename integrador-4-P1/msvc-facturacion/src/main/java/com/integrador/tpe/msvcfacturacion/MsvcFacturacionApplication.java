package com.integrador.tpe.msvcfacturacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.integrador.tpe.msvcfacturacion.clients")
public class MsvcFacturacionApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsvcFacturacionApplication.class, args);
    }

}
