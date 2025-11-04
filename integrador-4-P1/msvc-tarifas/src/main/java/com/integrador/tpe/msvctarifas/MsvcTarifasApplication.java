package com.integrador.tpe.msvctarifas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class MsvcTarifasApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsvcTarifasApplication.class, args);
    }

}
