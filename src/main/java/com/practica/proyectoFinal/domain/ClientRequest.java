package com.practica.proyectoFinal.domain;

import org.springframework.http.HttpStatus;

import com.practica.proyectoFinal.exception.IsbanException;
import com.practica.proyectoFinal.util.Code;
import com.practica.proyectoFinal.util.Description;
import com.practica.proyectoFinal.util.MessageMultiLanguageComponent;
import com.viewnext.util.Validation;

import lombok.Data;

@Data
public class ClientRequest {
	private String dni;
	private String nameClient;
	private String lastNameClient;
	private String email;
	private String phoneNumber;
	private String age;
	private String dateOfBirth;
	
	private MessageMultiLanguageComponent message;
	
	
	
	public void validateFields(MessageMultiLanguageComponent message) throws IsbanException {
		this.message = message;
		if (!Validation.validateDni(dni))  {
			throw new IsbanException(Code.CLIENT_DNI_ERROR_CODE.getValue(),message.getText(Description.DNI_NO_VALIDATE.getValue()),HttpStatus.CONFLICT);
		}
		
		if (!Validation.isLetterWithSpace(nameClient)) {
			throw new IsbanException(Code.CLIENT_NAME_ERROR_CODE.getValue(), message.getText(Description.NAME_NO_VALIDATE.getValue()),HttpStatus.CONFLICT);
		}
		
		if (!Validation.isLetterWithSpace(lastNameClient)) {
			throw new IsbanException(Code.CLIENT_LASTNAME_ERROR_CODE.getValue(), message.getText(Description.LASTNAME_NO_VALIDATE.getValue()),HttpStatus.CONFLICT);
		}
		
		if (!Validation.validateEmail(email)) {
			throw new IsbanException(Code.CLIENT_EMAIL_ERROR_CODE.getValue(), message.getText(Description.EMAIL_NO_VALIDATE.getValue()),HttpStatus.CONFLICT);
		}
		
		if (!Validation.validateNumber(Integer.parseInt(age))) {
			throw new IsbanException(Code.CLIENT_AGE_ERROR_CODE.getValue(), message.getText(Description.AGE_NO_VALIDATE.getValue()),HttpStatus.CONFLICT);
		}
		
		if (!Validation.validateDate(dateOfBirth)) {
			throw new IsbanException(Code.CLIENT_DATEOFBIRTH_ERROR_CODE.getValue(), message.getText(Description.DATEOFBIRTH_NO_VALIDATE.getValue()),HttpStatus.CONFLICT);
		}
		
	}



	public ClientRequest(String dni, String nameClient, String lastNameClient, String email, String age,
			String dateOfBirth) {
		super();
		this.dni = dni;
		this.nameClient = nameClient;
		this.lastNameClient = lastNameClient;
		this.email = email;
		this.age = age;
		this.dateOfBirth = dateOfBirth;
	}
	
}
