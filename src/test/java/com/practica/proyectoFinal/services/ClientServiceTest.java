package com.practica.proyectoFinal.services;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import com.practica.proyectoFinal.domain.ClientByDniRequest;
import com.practica.proyectoFinal.domain.ClientRequest;
import com.practica.proyectoFinal.domain.ClientResponse;
import com.practica.proyectoFinal.domain.FieldRequest;
import com.practica.proyectoFinal.domain.SuperResponse;
import com.practica.proyectoFinal.entity.Client;
import com.practica.proyectoFinal.entity.ClientPk;
import com.practica.proyectoFinal.exception.IsbanException;
import com.practica.proyectoFinal.repository.ClientRepository;
import com.practica.proyectoFinal.services.ClientService;
import com.practica.proyectoFinal.util.MessageMultiLanguageComponent;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ClientServiceTest {

	@InjectMocks
	private ClientService ClientService;

	@Mock
	private ClientRepository ClientRepository;

	@Mock
	private MessageMultiLanguageComponent message;

	private Client Client = new Client(new ClientPk("08896547L"), "Laura", "Espino", "l@gmail.com","666666666", "22",
			LocalDate.parse("08/10/1998", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
	private ClientResponse ClientResponse = new ClientResponse(Client);
	private ClientRequest ClientRequest = new ClientRequest("08896547L", "Laura", "Espino", "l@gmail.com", "22",
			"08/10/1998");

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetAllStudetsOk() throws IsbanException {
		List<Client> list = new ArrayList<>();
		list.add(Client);

		List<SuperResponse> listClientResponse = new ArrayList<>();
		listClientResponse.add(new ClientResponse(Client));

		Mockito.doReturn(list).when(ClientRepository).findAll();
		assertEquals(((ClientResponse) listClientResponse.get(0)).getDni(),
				((ClientResponse) ClientService.getAllClients().get(0)).getDni());
	}

	@Test(expected = IsbanException.class)
	public void testGetAllClientsNotFound() throws IsbanException {
		Mockito.doReturn(new ArrayList<Client>()).when(ClientRepository).findAll();
		ClientService.getAllClients();

	}

	@Test
	public void testGetClientsByPKOk() throws IsbanException {
		Mockito.doReturn(Client).when(ClientRepository).findByDni(Mockito.any());
		assertEquals(ClientResponse.getDni(),
				((ClientResponse) ClientService.getClientsByPK(new ClientByDniRequest("08896547L"))).getDni());

	}

	@Test(expected = IsbanException.class)
	public void testGetClientsByPkNotFound() throws IsbanException {
		Mockito.doThrow(new NullPointerException()).when(ClientRepository).findByDni(Mockito.any());
		ClientService.getClientsByPK(new ClientByDniRequest("08896547L"));
	}

	@Test(expected = Test.None.class)
	public void testSaveClientsOk() throws IsbanException {
		Mockito.doNothing().when(ClientRepository).save(Mockito.any());
		ClientService.saveClient(ClientRequest);
	}

	@Test(expected = IsbanException.class)
	public void testSaveClientsConflict() throws IsbanException {
		Mockito.doThrow(new DataIntegrityViolationException("")).when(ClientRepository).save(Mockito.any());
		ClientService.saveClient(ClientRequest);
	}

	@Test(expected = Test.None.class)
	public void testDeleteClientOk() throws IsbanException {
		Mockito.doNothing().when(ClientRepository).delete(Mockito.any());
		ClientService.deleteClient(new ClientByDniRequest("08896547L"));
	}

	@Test(expected = IsbanException.class)
	public void testDeleteClientConflict() throws IsbanException {
		Mockito.doThrow(new InvalidDataAccessApiUsageException("")).when(ClientRepository).delete(Mockito.any());
		ClientService.deleteClient(new ClientByDniRequest("08896547L"));
	}

	@Test(expected = Test.None.class)
	public void testUpdateClientOk() throws IsbanException {
		Mockito.doNothing().when(ClientRepository).update(Mockito.any());
		ClientService.updateClient(ClientRequest);
	}

	@Test(expected = IsbanException.class)
	public void testUpdateClientConflict() throws IsbanException {
		Mockito.doThrow(new NullPointerException()).when(ClientRepository).update(Mockito.any());
		ClientService.updateClient(ClientRequest);
	}

	@Test
	public void testFindByNameContainingOrLastNameContainingOk() throws IsbanException {
		List<Client> list = new ArrayList<>();
		list.add(Client);

		List<SuperResponse> listClientResponse = new ArrayList<>();
		listClientResponse.add(new ClientResponse(Client));

		Mockito.doReturn(list).when(ClientRepository)
				.findByNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(Mockito.any());
		assertEquals(((ClientResponse) listClientResponse.get(0)).getDni(), ((ClientResponse) ClientService
				.findByNameContainingOrLastNameContaining(new FieldRequest("non")).get(0)).getDni());

	}

	@Test(expected = IsbanException.class)
	public void testFindByNameContainingOrLastNameContainingError() throws IsbanException {
		Mockito.doReturn(new ArrayList<Client>()).when(ClientRepository)
				.findByNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(Mockito.any());
		ClientService.findByNameContainingOrLastNameContaining(new FieldRequest("non"));
	}

}
