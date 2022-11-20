package com.ticketapp.namingserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class NamingServerMain {
    public static void main(String[] args) {
        SpringApplication.run(NamingServerMain.class, args);
    }
}