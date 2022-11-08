package com.epam.training.ticketservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.epam.training.ticketservice")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
