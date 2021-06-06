package com.practica.proyectoFinal.domain;


import com.practica.proyectoFinal.entity.Client;
import com.viewnext.util.Util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponse extends SuperResponse{
	
	private String dni;
	private String name;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String age;
	private String dateOfBirth;
	
	public ClientResponse(Client client) {
		this.dni = client.getClientPk().getDni();
		this.name = client.getName();
		this.lastName = client.getLastName();
		this.email = client.getEmail();
		this.phoneNumber = client.getPhoneNumber();
		this.age = client.getAge();
		this.dateOfBirth = Util.localDateToString(client.getDateOfBirth());
	}
	
	
}
