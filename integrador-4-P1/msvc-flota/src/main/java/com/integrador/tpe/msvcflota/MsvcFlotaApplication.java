package com.integrador.tpe.msvcflota;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class MsvcFlotaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsvcFlotaApplication.class, args);
    }

}
