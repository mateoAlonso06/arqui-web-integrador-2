package com.integrador.tpe.msvcgroq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.integrador.tpe.msvcgroq.client")
@EnableDiscoveryClient
public class MsvcGroqApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsvcGroqApplication.class, args);
    }

}
