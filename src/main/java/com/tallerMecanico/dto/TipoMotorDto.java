package com.tallerMecanico.dto;

import com.tallerMecanico.entity.TipoMotor;

public record TipoMotorDto(long idTipoMotor, String tipoMotor) {
	
	public TipoMotorDto(TipoMotor tipoMotor) {
		this(tipoMotor.getIdTipoMotor(), tipoMotor.getTipoMotor());
	}

}
