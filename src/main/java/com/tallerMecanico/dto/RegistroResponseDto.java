package com.tallerMecanico.dto;


public class RegistroResponseDto {

	private String mensaje;
    private Long idUsuario;

    public RegistroResponseDto(String mensaje, Long idUsuario) {
        this.mensaje = mensaje;
        this.idUsuario = idUsuario;
    }

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
    
}
