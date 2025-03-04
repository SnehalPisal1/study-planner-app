package com.stadyplanner.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UserServiceApplication {

    public static void main(String[] str) {

        System.out.println("Executing UserServiceApplication....");

        SpringApplication.run(UserServiceApplication.class, str);

    }
}
