package com.practica.proyectoFinal.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.practica.proyectoFinal.domain.ClientByDniRequest;
import com.practica.proyectoFinal.domain.ClientRequest;
import com.practica.proyectoFinal.domain.ErrorResponse;
import com.practica.proyectoFinal.domain.FieldRequest;
import com.practica.proyectoFinal.domain.SuperResponse;
import com.practica.proyectoFinal.exception.IsbanException;
import com.practica.proyectoFinal.services.ClientService;


@RestController
public class ClientController {
	
	/** The client service. */
	@Autowired
	private ClientService clientService;
	
	/**
	 * List clients.
	 *
	 * @return the response entity
	 */
	@GetMapping(path = "/listAllClients")
	public ResponseEntity<List<SuperResponse>> listClients() {
		try {
			return new ResponseEntity<>(clientService.getAllClients(), HttpStatus.OK);
		} catch (IsbanException e) {
			List<SuperResponse> errorResponse = new ArrayList<>();
			errorResponse.add(new ErrorResponse(e.getMessage(),
					e.getDescription()));
			return new ResponseEntity<>(errorResponse,e.getHttpStatus());
		}
	}

	
	/**
	 * List client by pk.
	 *
	 * @param clientByDniRequest the client by dni request
	 * @return the response entity
	 */
	@GetMapping(path = "/listClientByPk")
	public ResponseEntity<SuperResponse> listClientByPk(ClientByDniRequest clientByDniRequest) {
		try {
			return new ResponseEntity<>(clientService.getClientsByPK(clientByDniRequest), HttpStatus.OK);
			
		} catch (IsbanException e) {
			return new ResponseEntity<>(new ErrorResponse(e.getMessage(),
					e.getDescription()), e.getHttpStatus());
		}
	}
	
	/**
	 * Save client.
	 *
	 * @param clientRequest the client request
	 * @return the response entity
	 */
	@RequestMapping(path = "/saveClient", method = RequestMethod.POST)
	public ResponseEntity<SuperResponse> saveClient(ClientRequest clientRequest){
		try {
			clientService.saveClient(clientRequest);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (IsbanException e) {
			return new ResponseEntity<>(new ErrorResponse(e.getMessage(), 
					e.getDescription()), e.getHttpStatus());
		}
	}

	
	/**
	 * Delete client.
	 *
	 * @param clientDeleteRequest the client delete request
	 * @return the response entity
	 * @throws IsbanException the isban exception
	 */
	@Transactional
	@RequestMapping(path = "/deleteClient", method = RequestMethod.DELETE)
	public ResponseEntity<SuperResponse> deleteClient(ClientByDniRequest clientDeleteRequest) throws IsbanException{
		try {
			clientService.deleteClient(clientDeleteRequest);
			return new ResponseEntity<>(HttpStatus.OK);
			
		} catch (IsbanException e) {
			return new ResponseEntity<>(new ErrorResponse(e.getMessage(), 
					e.getDescription()), e.getHttpStatus());
		}
	}
	
	
	/**
	 * Update client.
	 *
	 * @param clientRequest the client request
	 * @return the response entity
	 * @throws IsbanException the isban exception
	 */
	@RequestMapping(path = "/updateClient", method = RequestMethod.PUT)
	public ResponseEntity<SuperResponse> updateClient(ClientRequest clientRequest) throws IsbanException {
		try {
			clientService.updateClient(clientRequest);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (IsbanException e) {
			return new ResponseEntity<>(new ErrorResponse(e.getMessage(), 
					e.getDescription()), e.getHttpStatus());
		}
	}
	
	
	/**
	 * List client by name or last name.
	 *
	 * @param fieldRequest the field request
	 * @return the response entity
	 * @throws IsbanException the isban exception
	 */
	@GetMapping(path = "/listClientByNameOrLastName")
	public ResponseEntity<List<SuperResponse>> listClientByNameOrLastName(FieldRequest fieldRequest) throws IsbanException{
		try {
			return new ResponseEntity<>(clientService.findByNameContainingOrLastNameContaining(fieldRequest), HttpStatus.OK);
		} catch (IsbanException e) {
			List<SuperResponse> errorResponse = new ArrayList<>();
			errorResponse.add(new ErrorResponse(e.getMessage(),
					e.getDescription()));
			return new ResponseEntity<>(errorResponse,e.getHttpStatus());
		} 
	}
}
