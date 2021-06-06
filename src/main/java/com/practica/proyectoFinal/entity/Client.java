package com.practica.proyectoFinal.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "client")
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client{
	
	@EmbeddedId
	private ClientPk clientPk;
	
	@Column(name = "NAME")
	private String name;
	
	/** The last name. */
	@Column(name = "LASTNAME")
	private String lastName;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "PHONENUMBER")
	private String phoneNumber;
	
	@Column(name = "AGE")
	private String age;
	
//	@Temporal(value = TemporalType.DATE)
	@Column(name = "DATE_OF_BIRTH")
	private LocalDate dateOfBirth;
	



	
	
	
}
