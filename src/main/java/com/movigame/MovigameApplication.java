package com.movigame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.movigame"})
public class MovigameApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovigameApplication.class, args);
	}

}
