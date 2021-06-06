package com.practica.proyectoFinal.repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.practica.proyectoFinal.domain.ClientByDniRequest;
import com.practica.proyectoFinal.domain.ClientRequest;
import com.practica.proyectoFinal.entity.Client;
import com.practica.proyectoFinal.entity.ClientPk;

@Repository
public class ClientRepository {
	@PersistenceContext
	private EntityManager instanciaBBDD;

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<Client> findAll() {
		CriteriaBuilder criteriaBuilder = instanciaBBDD.getCriteriaBuilder();
		CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
		Root<Client> from = criteriaQuery.from(Client.class);

		CriteriaQuery<Client> consulta = criteriaQuery.select(from);

		return instanciaBBDD.createQuery(consulta).getResultList();
	}
 
	public Client findByDni(ClientPk clientPk) {
		return instanciaBBDD.find(Client.class, clientPk);
		
	}
	
	/**
	 * Save.
	 *
	 * @param clientRequest the client request
	 */
	@Transactional
	public void save(ClientRequest clientRequest) {
		instanciaBBDD.persist(new Client(new ClientPk(clientRequest.getDni()), clientRequest.getNameClient()
				, clientRequest.getLastNameClient(), clientRequest.getEmail(),clientRequest.getPhoneNumber(), clientRequest.getAge(), 
				LocalDate.parse(clientRequest.getDateOfBirth(), DateTimeFormatter.ofPattern("dd/MM/yyyy"))) );
	}

	/**
	 * Delete.
	 *
	 * @param clientByDniRequest the client by dni request
	 */
	@Transactional
	public void delete(ClientByDniRequest clientByDniRequest) {
		instanciaBBDD.remove(findByDni(new ClientPk(clientByDniRequest.getDni())));
	}

	/**
	 * Update.
	 *
	 * @param clientRequest the client request
	 */
	@Transactional
	public void update(ClientRequest clientRequest) {
		Client clientUpdate = findByDni(new ClientPk(clientRequest.getDni()));
		clientUpdate.setName(clientRequest.getNameClient());
		clientUpdate.setLastName(clientRequest.getLastNameClient());
		clientUpdate.setEmail(clientRequest.getEmail());
		clientUpdate.setPhoneNumber(clientRequest.getPhoneNumber());
		clientUpdate.setAge(clientRequest.getAge());
		clientUpdate.setDateOfBirth(LocalDate.parse(clientRequest.getDateOfBirth(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));

		instanciaBBDD.persist(clientUpdate);
	}

	/**
	 * Find by name containing ignore case or last name containing ignore case.
	 *
	 * @param nameOrLastName the name or last name
	 * @return the list
	 */
	public List<Client> findByNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String nameOrLastName){
		CriteriaBuilder criteriaBuilder = instanciaBBDD.getCriteriaBuilder();
		CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
		Root<Client> from = criteriaQuery.from(Client.class);
		
		CriteriaQuery<Client> consulta = criteriaQuery.select(from);
		
		Predicate likeName = criteriaBuilder.like(criteriaBuilder.lower(from.get("nameClient")), "%" + nameOrLastName.toLowerCase() + "%");
		Predicate likeLastName = criteriaBuilder.like(criteriaBuilder.lower(from.get("lastNameClient")), "%" + nameOrLastName.toLowerCase() + "%");
		
		consulta.where(criteriaBuilder.or(likeName, likeLastName));
		return instanciaBBDD.createQuery(consulta).getResultList();
	}
}
