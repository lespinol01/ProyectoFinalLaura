package com.practica.proyectoFinal.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.practica.proyectoFinal.domain.ClientByDniRequest;
import com.practica.proyectoFinal.domain.ClientRequest;
import com.practica.proyectoFinal.domain.ClientResponse;
import com.practica.proyectoFinal.domain.FieldRequest;
import com.practica.proyectoFinal.domain.SuperResponse;
import com.practica.proyectoFinal.entity.Client;
import com.practica.proyectoFinal.entity.ClientPk;
import com.practica.proyectoFinal.exception.IsbanException;
import com.practica.proyectoFinal.repository.ClientRepository;
import com.practica.proyectoFinal.util.Code;
import com.practica.proyectoFinal.util.Description;
import com.practica.proyectoFinal.util.MessageMultiLanguageComponent;

@Service
public class ClientService implements IClientService {
	
	/** The client repository. */
	@Autowired
	private ClientRepository clientRepository;

	/** The message. */
	@Autowired
	private MessageMultiLanguageComponent message;
	
	/**
	 * Gets the all clients.
	 *
	 * @return the all clients
	 * @throws IsbanException the isban exception
	 */
	@Override
	public List<SuperResponse> getAllClients() throws IsbanException {
		List<SuperResponse> listAllResponse = new ArrayList<>();
		for (Client element : clientRepository.findAll()) {
			listAllResponse.add(new ClientResponse(element));
		}
		if (listAllResponse.isEmpty()) {
			throw new IsbanException(Code.CLIENT_LIST_EMPTY.getValue(), message.getText(Description.CLIENT_LIST_EMPTY.getValue()), HttpStatus.NOT_FOUND);
		}
		return listAllResponse;
	}

	/**
	 * Gets the clients by PK.
	 *
	 * @param clientByDniRequest the client by dni request
	 * @return the clients by PK
	 * @throws IsbanException the isban exception
	 */
	@Override
	public SuperResponse getClientsByPK(ClientByDniRequest clientByDniRequest) throws IsbanException {
		clientByDniRequest.validateField(message);
		try {
			return new ClientResponse(clientRepository.findByDni(new ClientPk(clientByDniRequest.getDni())));
		} catch (NullPointerException nullPointerException) {
			throw new IsbanException(Code.CLIENT_NO_EXIST_CODE.getValue(),
					message.getText(Description.CLIENT_NO_EXIST_DESCRIPTION.getValue()), HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Save clients.
	 *
	 * @param clientRequest the client request
	 * @throws IsbanException the isban exception
	 */
	@Override
	public void saveClient(ClientRequest clientRequest) throws IsbanException {

		clientRequest.validateFields(message);
		try {
			clientRepository.save(clientRequest);
			
		} catch (Exception e) {
			throw new IsbanException(Code.CLIENT_ALREADY_EXIST_CODE.getValue(),
					message.getText(Description.CLIENT_DNI_EXIST.getValue()), HttpStatus.CONFLICT);
		}
	}

	/**
	 * Delete client.
	 *
	 * @param clientByDniRequest the client by dni request
	 * @throws IsbanException the isban exception
	 */
	@Override
	public void deleteClient(ClientByDniRequest clientByDniRequest) throws IsbanException {
		clientByDniRequest.validateField(message);
		try {
			clientRepository.delete(clientByDniRequest);
			
		} catch (InvalidDataAccessApiUsageException invalidDataAccessApiUsageException) {
			throw new IsbanException(Code.CLIENT_NO_EXIST_CODE.getValue(),
			message.getText(Description.CLIENT_NO_EXIST_DESCRIPTION.getValue()), HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Update client.
	 *
	 * @param clientRequest the client request
	 * @throws IsbanException the isban exception
	 */
	@Override
	public void updateClient(ClientRequest clientRequest) throws IsbanException {

		clientRequest.validateFields(message);
		try {
			clientRepository.update(clientRequest);
			
		} catch (Exception e) {
			throw new IsbanException(Code.CLIENT_ALREADY_EXIST_CODE.getValue(),
					message.getText(Description.CLIENT_DNI_EXIST.getValue()), HttpStatus.CONFLICT);
		}
	}

	
	
	/**
	 * Find by name containing or last name containing.
	 *
	 * @param fieldRequest the field request
	 * @return the list
	 * @throws IsbanException 
	 */
	@Override
	public List<SuperResponse> findByNameContainingOrLastNameContaining(FieldRequest fieldRequest) throws IsbanException {
		List<Client> list = clientRepository.findByNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
				fieldRequest.getField());
		List<SuperResponse> listAllResponse = new ArrayList<>();
		for (Client client : list) {
			listAllResponse.add(new ClientResponse(client));
		}
		if (list.isEmpty()) {
			throw new IsbanException(Code.CLIENT_LIST_EMPTY.getValue(), message.getText(Description.CLIENT_LIST_EMPTY.getValue()), HttpStatus.NOT_FOUND);
		}else {
			return listAllResponse;
		}
	}

}
