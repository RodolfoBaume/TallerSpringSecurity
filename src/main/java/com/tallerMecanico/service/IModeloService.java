package com.tallerMecanico.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tallerMecanico.dto.ModeloDto;
import com.tallerMecanico.entity.Modelo;

public interface IModeloService {
	
	List<Modelo> findAll();
	
	Modelo findById(Long idModelo);
	
	Page<Modelo> findAllPage(Pageable pageable);
	
	Modelo createModelo(ModeloDto modelo);
	
	Modelo deleteModelo(Long idModelo);
	
	Modelo updateModelo(Long idModelo, ModeloDto modelo);
}
