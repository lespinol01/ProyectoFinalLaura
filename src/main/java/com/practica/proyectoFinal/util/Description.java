package com.practica.proyectoFinal.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Description {
	
			CLIENT_NO_EXIST_DESCRIPTION("client.no.exist.description"), CLIENT_NO_EXIST_DNI_DESCRIPTION("client.no.exist.dni.description"),
			 DNI_NO_VALIDATE("dni.no.validate"),NAME_NO_VALIDATE("name.no.validate"), LASTNAME_NO_VALIDATE("lastname.no.validate"),
			 EMAIL_NO_VALIDATE("email.no.validate"),AGE_NO_VALIDATE("age.no.validate"),DATEOFBIRTH_NO_VALIDATE("dateOfBirth.no.validate"),
			 CLIENT_LIST_EMPTY("client.list.empty"),CLIENT_DNI_EXIST("client.dni.exist");
	
	private String value;

}
