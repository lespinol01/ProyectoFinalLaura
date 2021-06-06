package com.practica.proyectoFinal.controllers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.practica.proyectoFinal.controllers.ClientController;
import com.practica.proyectoFinal.domain.ClientByDniRequest;
import com.practica.proyectoFinal.domain.ClientResponse;
import com.practica.proyectoFinal.domain.ErrorResponse;
import com.practica.proyectoFinal.domain.FieldRequest;
import com.practica.proyectoFinal.domain.SuperResponse;
import com.practica.proyectoFinal.exception.IsbanException;
import com.practica.proyectoFinal.services.ClientService;
import com.practica.proyectoFinal.util.Code;
import com.practica.proyectoFinal.util.Description;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ClientControllerTest {
	
	@InjectMocks
	private ClientController controller;
	
	@Mock
	private ClientService clientService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testListAllOK() throws IsbanException {
		List<SuperResponse> list = new ArrayList<>();
		
		Mockito.doReturn(list).when(clientService).getAllClients();
		assertEquals(list, controller.listClients().getBody());
	}
	
	@Test
	public void testListAllNotFound() throws IsbanException {
		Mockito.doThrow(new IsbanException(Code.CLIENT_LIST_EMPTY.getValue(), Description.CLIENT_LIST_EMPTY.getValue(), HttpStatus.NOT_FOUND)).when(clientService).getAllClients();
		assertEquals(Code.CLIENT_LIST_EMPTY.getValue(),((ErrorResponse) controller.listClients().getBody().get(0)).getCode());
	}
	
	@Test
	public void testListByDniOk() throws IsbanException {
		ClientByDniRequest clientByDniRequest = new ClientByDniRequest("55555555L");
		ClientResponse clientResponse = new ClientResponse();
		Mockito.doReturn(clientResponse).when(clientService).getClientsByPK(clientByDniRequest);
		assertEquals(clientResponse, controller.listClientByPk(clientByDniRequest).getBody());
	}
	
	@Test
	public void testListByDniNotFound() throws IsbanException {
		ClientByDniRequest clientByDniRequest = new ClientByDniRequest("08852471M");
		Mockito.doThrow(new IsbanException(Code.CLIENT_NO_EXIST_CODE.getValue(),
				Description.CLIENT_NO_EXIST_DNI_DESCRIPTION.getValue(), HttpStatus.NOT_FOUND)).when(clientService).getClientsByPK(clientByDniRequest);
		assertEquals(Code.CLIENT_NO_EXIST_CODE.getValue(), ((ErrorResponse) controller.listClientByPk(clientByDniRequest).getBody()).getCode());
		
	}
	
	@Test
	public void testSaveclient() throws IsbanException {
		Mockito.doNothing().when(clientService).saveClient(Mockito.any());
		assertEquals(HttpStatus.OK, controller.saveClient(Mockito.any()).getStatusCode());
	}
	
	@Test
	public void testSaveclientConflict() throws IsbanException {
		Mockito.doThrow(new IsbanException(Code.CLIENT_ALREADY_EXIST_CODE.getValue(),
				Description.CLIENT_DNI_EXIST.getValue(), HttpStatus.CONFLICT)).when(clientService).saveClient(Mockito.any());
		assertEquals(Code.CLIENT_ALREADY_EXIST_CODE.getValue(), ((ErrorResponse) controller.saveClient(Mockito.any()).getBody()).getCode());
	}
	
	@Test
	public void testDeleteclientOk() throws IsbanException {
		ClientByDniRequest clientByDniRequest = new ClientByDniRequest("55555555L");
		Mockito.doNothing().when(clientService).deleteClient(clientByDniRequest);
		assertEquals(HttpStatus.OK, controller.deleteClient(clientByDniRequest).getStatusCode());
	}
	
	@Test
	public void testDeleteclientConflic() throws IsbanException {
		ClientByDniRequest clientByDniRequest = new ClientByDniRequest("55555555L");
		Mockito.doThrow(new IsbanException(Code.CLIENT_NO_EXIST_CODE.getValue(),
				Description.CLIENT_NO_EXIST_DNI_DESCRIPTION.getValue(), HttpStatus.NOT_FOUND)).when(clientService).deleteClient(clientByDniRequest);
		assertEquals(Code.CLIENT_NO_EXIST_CODE.getValue(), ((ErrorResponse) controller.deleteClient(clientByDniRequest).getBody()).getCode());
	}
	
	@Test
	public void testUpdateclientOk() throws IsbanException {
		Mockito.doNothing().when(clientService).updateClient(Mockito.any());
		assertEquals(HttpStatus.OK, controller.updateClient(Mockito.any()).getStatusCode());
	}
	
	@Test
	public void testUpdateclientConflict() throws IsbanException {
		Mockito.doThrow(new IsbanException(Code.CLIENT_ALREADY_EXIST_CODE.getValue(),
				Description.CLIENT_DNI_EXIST.getValue(), HttpStatus.CONFLICT)).when(clientService).updateClient(Mockito.any());
		assertEquals(Code.CLIENT_ALREADY_EXIST_CODE.getValue(), ((ErrorResponse) controller.updateClient(Mockito.any()).getBody()).getCode());
	}
	
	@Test
	public void testFindByNameContainingOrLastNameContainingOk() throws IsbanException {
		List<SuperResponse> list = new ArrayList<>();
		FieldRequest fieldRequest = new FieldRequest();
		
		Mockito.doReturn(list).when(clientService).findByNameContainingOrLastNameContaining(fieldRequest);
		assertEquals(list, controller.listClientByNameOrLastName(fieldRequest).getBody());
	}
	
	@Test
	public void testFindByNameContainingOrLastNameContainingNotFound() throws IsbanException {
		FieldRequest fieldRequest = new FieldRequest();
		Mockito.doThrow(new IsbanException(Code.CLIENT_LIST_EMPTY.getValue(), Description.CLIENT_LIST_EMPTY.getValue(), HttpStatus.NOT_FOUND)).when(clientService).findByNameContainingOrLastNameContaining(fieldRequest);
		assertEquals(Code.CLIENT_LIST_EMPTY.getValue(),((ErrorResponse) controller.listClientByNameOrLastName(fieldRequest).getBody().get(0)).getCode());
	}

}
