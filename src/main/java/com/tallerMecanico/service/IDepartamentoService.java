package com.tallerMecanico.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tallerMecanico.dto.DepartamentoDto;
import com.tallerMecanico.entity.Departamento;

public interface IDepartamentoService {

	List<Departamento> findAll();
	
	Page<Departamento> findAllPage(Pageable pageable);
	
	Departamento findById(Long idDepartamento);
	
	Departamento createMarca(DepartamentoDto departamento);
	
	Departamento deleteMarca(Long idDepartamento);
	
	Departamento updateMarca(Long idDepartamento, DepartamentoDto departamento);
}
