package com.agency04.heist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class HeistApplication {

	public static void main(String[] args) {
		SpringApplication.run(HeistApplication.class, args);
	}

}
