package com.viewnext.isban.practicas.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.practica.proyectoFinal.domain.ClientByDniRequest;
import com.practica.proyectoFinal.domain.ClientRequest;
import com.practica.proyectoFinal.entity.Client;
import com.practica.proyectoFinal.entity.ClientPk;
import com.practica.proyectoFinal.repository.ClientRepository;

public class ClientRepositoryTest {

	@InjectMocks
	private ClientRepository ClientRepository;

	@Mock
	private EntityManager instanceBBDD;

	@Mock
	private CriteriaBuilderImpl criteriaBuilder;

	@Mock
	TypedQuery<Client> typedQuery;

	@Mock
	private CriteriaQuery<Client> query;

	@Mock
	private CriteriaQuery<Client> selectAll;

	@Mock
	private Root<Client> from;

	private final Client Client = new Client(new ClientPk("08896547L"), "Laura", "Espino", "l@gmail.com","666666666","22",
			LocalDate.parse("08/10/1998", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
	private final ClientRequest ClientRequest = new ClientRequest("55555555R", "Laura", "Espino", "l@gmail.com", "22", "25/08/1998");

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	
	
	@Test()
	public void testFindAll() {
		List<Client> list = new ArrayList<>();
		list.add(Client);

		builderMocks(list);
		assertEquals(list, ClientRepository.findAll());
	}

	@Test
	public void testFindByDni() {
		Mockito.doReturn(Client).when(instanceBBDD).find(Mockito.any(), Mockito.any());
		assertEquals(Client, ClientRepository.findByDni(new ClientPk("55555555R")));
	}

	@Test(expected = Test.None.class)
	public void testSave() {
		Mockito.doNothing().when(instanceBBDD).persist(Mockito.any());
		ClientRepository.save(ClientRequest);
	}

	@Test(expected = Test.None.class)
	public void testDelete() {
		Mockito.doNothing().when(instanceBBDD).remove(Mockito.any());
		ClientRepository.delete(new ClientByDniRequest("55555555R"));
	}

	@Test(expected = Test.None.class)
	public void testUpdate() {
		Mockito.doReturn(Client).when(instanceBBDD).find(Mockito.any(), Mockito.any());
		Mockito.doNothing().when(instanceBBDD).persist(Mockito.any());
		ClientRepository.update(ClientRequest);
	}

	@Test
	public void testFindByNameContainingIgnoreCaseOrLastNameContainingIgnoreCase() {
		List<Client> list = new ArrayList<>();
		list.add(Client);
		
		builderMocks(list);
		assertEquals(list, ClientRepository.findByNameContainingIgnoreCaseOrLastNameContainingIgnoreCase("non"));
	}

	
	private void builderMocks(List<Client> ClientList) {
        Mockito.doReturn(criteriaBuilder).when(instanceBBDD).getCriteriaBuilder();
        Mockito.doReturn(query).when(criteriaBuilder).createQuery(Client.class);
        Mockito.doReturn(from).when(query).from(Client.class);
        Mockito.doReturn(selectAll).when(query).select(from);
        Mockito.doReturn(typedQuery).when(instanceBBDD).createQuery(selectAll);
        Mockito.doReturn(ClientList).when(typedQuery).getResultList();
    }
}
