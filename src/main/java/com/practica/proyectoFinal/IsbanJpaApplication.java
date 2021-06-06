package com.practica.proyectoFinal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class IsbanJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(IsbanJpaApplication.class, args);
		log.info("The application started successfully");
	}

}
 