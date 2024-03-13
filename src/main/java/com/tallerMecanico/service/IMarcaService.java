package com.tallerMecanico.service;

import java.util.List;

import com.tallerMecanico.dto.MarcaDto;
import com.tallerMecanico.entity.Marca;

public interface IMarcaService {

	List<Marca> findAll();
	
	Marca findById(Long idMarca);
	
	Marca createMarca(MarcaDto marca);
	
	Marca deleteMarca(Long idMarca);
	
	Marca updateMarca(Long idMarca, MarcaDto marca);
}
