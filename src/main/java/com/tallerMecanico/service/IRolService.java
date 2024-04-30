package com.tallerMecanico.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tallerMecanico.dto.RolDto;
import com.tallerMecanico.entity.Rol;

public interface IRolService {

	List<Rol> findAll();
	
	Page<Rol> findAllPage(Pageable pageable);
	
	Rol findById(Long idRol);
	
	Rol createRol(RolDto rol);
	
	Rol deleteRol(Long idRol);
	
	Rol updateRol(Long idRol, RolDto rol);
}
