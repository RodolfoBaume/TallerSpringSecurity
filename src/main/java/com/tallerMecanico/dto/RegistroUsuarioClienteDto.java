package com.tallerMecanico.dto;


public class RegistroUsuarioClienteDto {
	private UsuarioDto usuario;
    private ClienteDto cliente;
	
    
    public RegistroUsuarioClienteDto(UsuarioDto usuario, ClienteDto cliente) {
		super();
		this.usuario = usuario;
		this.cliente = cliente;
	}


	public UsuarioDto getUsuario() {
		return usuario;
	}


	public void setUsuario(UsuarioDto usuario) {
		this.usuario = usuario;
	}


	public ClienteDto getCliente() {
		return cliente;
	}


	public void setCliente(ClienteDto cliente) {
		this.cliente = cliente;
	}
    
}
