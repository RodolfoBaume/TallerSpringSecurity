package com.tallerMecanico.service;

import java.util.List;

import com.tallerMecanico.dto.ModeloDto;
import com.tallerMecanico.entity.Modelo;

public interface IModeloService {
	
	List<Modelo> findAll();
	
	Modelo findById(Long idModelo);
	
	Modelo createModelo(ModeloDto modelo);
	
	Modelo deleteModelo(Long idModelo);
	
	Modelo updateModelo(Long idModelo, ModeloDto modelo);
}
