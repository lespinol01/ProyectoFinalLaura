package com.practica.proyectoFinal.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse extends SuperResponse{
	private String code;
	private String description;
}
