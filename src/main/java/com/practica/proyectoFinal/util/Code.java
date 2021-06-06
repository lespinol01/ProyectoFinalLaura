package com.practica.proyectoFinal.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Code {

	CLIENT_NO_EXIST_CODE("Client_01"),
	CLIENT_ALREADY_EXIST_CODE("Client_02"),
	CLIENT_DNI_ERROR_CODE("Client_03"),
	CLIENT_NAME_ERROR_CODE("Client_04"),
	CLIENT_LASTNAME_ERROR_CODE("Client_05"),
	CLIENT_EMAIL_ERROR_CODE("Client_06"),
	CLIENT_AGE_ERROR_CODE("Client_07"),
	CLIENT_DATEOFBIRTH_ERROR_CODE("Client_08"),
	CLIENT_LIST_EMPTY("Client_09");
	
	
	private String value;


}
