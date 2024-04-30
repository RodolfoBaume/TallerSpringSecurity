package com.tallerMecanico.dto;

public class RegistroUsuarioEmpleadoDto {
	private UsuarioDto usuario;
	private EmpleadoDto empleado;
	
	public RegistroUsuarioEmpleadoDto(UsuarioDto usuario, EmpleadoDto empleado) {
		super();
		this.usuario = usuario;
		this.empleado = empleado;
	}

	public UsuarioDto getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDto usuario) {
		this.usuario = usuario;
	}

	public EmpleadoDto getEmpleado() {
		return empleado;
	}

	public void setEmpleado(EmpleadoDto empleado) {
		this.empleado = empleado;
	}
}
