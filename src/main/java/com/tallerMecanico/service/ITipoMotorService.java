package com.tallerMecanico.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tallerMecanico.dto.TipoMotorDto;
import com.tallerMecanico.entity.TipoMotor;

public interface ITipoMotorService {

	List<TipoMotor> findAll();
	
	Page<TipoMotor> findAllPage(Pageable pageable);
	
	TipoMotor findById(Long idTipoMotor);
	
	TipoMotor createTipoMotor(TipoMotorDto tipoMotor);
	
	TipoMotor deleteTipoMotor(Long idTipoMotor);
	
	TipoMotor updateTipoMotor(Long idTipoMotor, TipoMotorDto tipoMotor);
	
}
