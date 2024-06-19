package com.tallerMecanico.dto;

public class UsuarioActualDto {
	private String username;
	private String password;
	private String role;
	private Long idCliente;
	private Long idEmpleado;
	
	public UsuarioActualDto() {
		super();
	}


	public UsuarioActualDto(String username, String password, String role) {
		super();
		this.username = username;
		this.password = password;
		this.role = role;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}

	public Long getIdCliente() {
		return idCliente;
	}


	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}


	public Long getIdEmpleado() {
		return idEmpleado;
	}


	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	
}
