package com.integrador.tpe.msvcgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.integrador.tpe.msvcgateway.client")
@EnableDiscoveryClient
public class MsvcGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsvcGatewayApplication.class, args);
    }

}
