package com.tallerMecanico.projection;

import com.tallerMecanico.entity.Cliente;

public class ClienteProjectionImpl implements IClienteProjection {
	
	private Cliente cliente;
	
	public ClienteProjectionImpl(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public long getIdCliente() {
		return cliente.getIdCliente();
	}

	@Override
	public String getNombre() {
		return cliente.getNombre();
	}

	@Override
	public String getApellidoPaterno() {
		return cliente.getApellidoPaterno();
	}

	@Override
	public String getApellidoMaterno() {
		return cliente.getApellidoMaterno();
	}

	@Override
	public String getDomicilio() {
		return cliente.getDomicilio();
	}

	@Override
	public String getTelefono() {
		return cliente.getTelefono();
	}

}
