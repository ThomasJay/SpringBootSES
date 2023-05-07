package com.thomasjayconsulting.springbootses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringBootSesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSesApplication.class, args);
    }

}
