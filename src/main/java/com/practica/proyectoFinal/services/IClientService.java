package com.practica.proyectoFinal.services;

import java.util.List;

import com.practica.proyectoFinal.domain.ClientByDniRequest;
import com.practica.proyectoFinal.domain.ClientRequest;
import com.practica.proyectoFinal.domain.FieldRequest;
import com.practica.proyectoFinal.domain.SuperResponse;
import com.practica.proyectoFinal.exception.IsbanException;

public interface IClientService {
	public List<SuperResponse> getAllClients() throws IsbanException;
	public SuperResponse getClientsByPK(ClientByDniRequest clientByDniRequest) throws IsbanException;
	public void saveClient(ClientRequest clientRequest) throws IsbanException;
	public void deleteClient(ClientByDniRequest clientDeleteRequest) throws IsbanException;
	public void updateClient(ClientRequest clientRequest)  throws IsbanException;
	public List<SuperResponse> findByNameContainingOrLastNameContaining(FieldRequest fieldRequest) throws IsbanException;
}
