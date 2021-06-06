package com.practica.proyectoFinal.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientPk implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "DNI", length = 9)
	private String dni;
}
