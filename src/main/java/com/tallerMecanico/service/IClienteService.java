package com.tallerMecanico.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tallerMecanico.dto.ClienteDto;
import com.tallerMecanico.entity.Cliente;
import com.tallerMecanico.projection.IClienteClosedView;

public interface IClienteService {

	List<Cliente> findAll();
	
	Page<Cliente> findAllPage(Pageable pageable);
	
	Cliente findById(Long idCliente);
	
	Cliente createCliente(ClienteDto cliente, Long idUsuario);
	
	Cliente deleteCliente(Long idCliente);
	
	Cliente updateCliente(Long idCliente, ClienteDto cliente);
	
	List<Cliente> buscarClientesPorNombreApellidoPaternoApellidoMaternoTelefono(String searchTerm);
	
	Page<IClienteClosedView> getAllClientesWithVehiculos(Pageable pageable);
	
	List<IClienteClosedView> getAllClientesWithVehiculos();
}
