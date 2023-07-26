package com.example.myapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
@Configuration
@ComponentScan(basePackages = {"com.example.myapp"})
@SpringBootApplication
public class SpringMiniPrj3thApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMiniPrj3thApplication.class, args);
	}

}
