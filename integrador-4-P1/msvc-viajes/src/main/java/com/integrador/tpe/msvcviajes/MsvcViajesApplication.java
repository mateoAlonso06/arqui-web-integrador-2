package com.integrador.tpe.msvcviajes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.integrador.tpe.msvcviajes.clients")
public class MsvcViajesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsvcViajesApplication.class, args);
    }

}
