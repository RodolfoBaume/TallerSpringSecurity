package com.tallerMecanico.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tallerMecanico.dto.MarcaDto;
import com.tallerMecanico.entity.Marca;

public interface IMarcaService {

	List<Marca> findAll();
	
	Page<Marca> findAllPage(Pageable pageable);
	
	Marca findById(Long idMarca);
	
	Marca createMarca(MarcaDto marca);
	
	Marca deleteMarca(Long idMarca);
	
	Marca updateMarca(Long idMarca, MarcaDto marca);
}
