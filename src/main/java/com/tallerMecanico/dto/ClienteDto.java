package com.tallerMecanico.dto;


import com.tallerMecanico.entity.Cliente;
import com.tallerMecanico.entity.Usuario;


public record ClienteDto(
		long idCliente, 
		String nombre, 
		String apellidoPaterno, 
		String apellidoMaterno, 
		String domicilio, 
		String telefono, 
		Usuario usuario
		) {
	
	public ClienteDto(Cliente cliente) {
		this(cliente.getIdCliente(), cliente.getNombre(), cliente.getApellidoPaterno(), cliente.getApellidoMaterno(), cliente.getDomicilio(), cliente.getTelefono(), cliente.getUsuario() );
	}

}
