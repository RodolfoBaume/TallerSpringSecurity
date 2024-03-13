package com.tallerMecanico.service;

import java.util.List;

import com.tallerMecanico.dto.ClienteDto;
import com.tallerMecanico.entity.Cliente;

public interface IClienteService {

	List<Cliente> findAll();
	
	Cliente findById(Long idCliente);
	
	Cliente createCliente(ClienteDto cliente);
	
	Cliente deleteCliente(Long idCliente);
	
	Cliente updateCliente(Long idCliente, ClienteDto cliente);
	
	
}
