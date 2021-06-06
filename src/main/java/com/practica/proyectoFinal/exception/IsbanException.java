package com.practica.proyectoFinal.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class IsbanException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final String message;
	private final String description;
	private final HttpStatus httpStatus;
	
	

}
