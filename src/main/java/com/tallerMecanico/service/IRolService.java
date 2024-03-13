package com.tallerMecanico.service;

import java.util.List;

import com.tallerMecanico.dto.RolDto;
import com.tallerMecanico.entity.Rol;

public interface IRolService {

	List<Rol> findAll();
	
	Rol findById(Long idRol);
	
	Rol createRol(RolDto rol);
	
	Rol deleteRol(Long idRol);
	
	Rol updateRol(Long idRol, RolDto rol);
}
