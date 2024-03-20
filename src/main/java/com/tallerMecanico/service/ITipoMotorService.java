package com.tallerMecanico.service;

import java.util.List;

import com.tallerMecanico.dto.TipoMotorDto;
import com.tallerMecanico.entity.TipoMotor;

public interface ITipoMotorService {

	List<TipoMotor> findAll();
	
	TipoMotor findById(Long idTipoMotor);
	
	TipoMotor createTipoMotor(TipoMotorDto tipoMotor);
	
	TipoMotor deleteTipoMotor(Long idTipoMotor);
	
	TipoMotor updateTipoMotor(Long idTipoMotor, TipoMotorDto tipoMotor);
	
}
