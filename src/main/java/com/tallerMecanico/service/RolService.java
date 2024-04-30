package com.tallerMecanico.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tallerMecanico.dto.RolDto;
import com.tallerMecanico.entity.Rol;
import com.tallerMecanico.repository.IRolRepository;

@Service
public class RolService implements IRolService {

	@Autowired
	private IRolRepository rolRepository;

	// Consulta todos
	@Transactional(readOnly = true)
	public List<Rol> findAll() {
		return (List<Rol>) rolRepository.findAll();
	}
	
	// consulta todos para paginación
	@Transactional(readOnly = true)
	public Page<Rol> findAllPage(Pageable pageable) {
		return rolRepository.findAll(pageable);
	}

	// consulta por id
	@Transactional(readOnly = true)
	public Rol findById(Long idRol) {
		return rolRepository.findById(idRol).orElse(null);
	}

	// Crear
	@Transactional
	public Rol createRol(RolDto rol) {
		Rol rolEntity = new Rol();
		rolEntity.setNombre(rol.nombre());
		return rolRepository.save(rolEntity);
	}

	// Eliminar
	public Rol deleteRol(Long idRol) {
		rolRepository.deleteById(idRol);
		return null;
	}

	// Modificar
	@Transactional
	public Rol updateRol(Long idRol, RolDto rol) {
		Rol rolEntity = rolRepository.findById(idRol)
				.orElseThrow(() -> new NoSuchElementException("Rol no encontrado con el ID: " + idRol));
		rolEntity.setNombre(rol.nombre());
		return rolRepository.save(rolEntity);
	}
}
