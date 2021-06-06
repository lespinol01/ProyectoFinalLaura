package com.practica.proyectoFinal.domain;

import org.springframework.http.HttpStatus;

import com.practica.proyectoFinal.exception.IsbanException;
import com.practica.proyectoFinal.util.Code;
import com.practica.proyectoFinal.util.Description;
import com.practica.proyectoFinal.util.MessageMultiLanguageComponent;
import com.viewnext.util.Validation;

import lombok.Data;

@Data
public class ClientByDniRequest {
	private String dni;
	
	private MessageMultiLanguageComponent message;
	
	public ClientByDniRequest(String dni) {
		this.dni = dni;
	}
	
	public void validateField(MessageMultiLanguageComponent message) throws IsbanException {
		this.message = message;
		if (!Validation.validateDni(dni)) {
			throw new IsbanException(Code.CLIENT_DNI_ERROR_CODE.getValue(),message.getText(Description.DNI_NO_VALIDATE.getValue()), HttpStatus.CONFLICT);
		}
		
	}
}
