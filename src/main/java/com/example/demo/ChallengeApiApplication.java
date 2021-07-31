package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * http://localhost:8080/h2/
 * http://localhost:8080/swagger-ui/index.html
 */
@SpringBootApplication
@EnableFeignClients
public class ChallengeApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeApiApplication.class, args);
	}

}
