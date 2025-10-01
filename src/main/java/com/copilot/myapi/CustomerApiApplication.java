package com.copilot.myapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication(scanBasePackages = "com.copilot.myapi")
@OpenAPIDefinition(info = @Info(title = "Customer API", version = "1.0", description = "Customer Management API"))
public class CustomerApiApplication {
    public static void main(String[] args) {
        System.setProperty("spring.profiles.default", "local");
        SpringApplication.run(CustomerApiApplication.class, args);
    }
}