package com.studyplanner.authservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthServiceApplication {

    public static void main(String[] args){

        System.out.println("Executing AuthServiceApplication.....");

        SpringApplication.run(AuthServiceApplication.class,args);
    }

}
