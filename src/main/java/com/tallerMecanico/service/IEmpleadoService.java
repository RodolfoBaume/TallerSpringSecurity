package com.tallerMecanico.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tallerMecanico.dto.EmpleadoDto;
import com.tallerMecanico.entity.Empleado;

public interface IEmpleadoService {

	List<Empleado> findAll();
	
	Page<Empleado> findAllPage(Pageable pageable);
	
	Empleado findById(Long idEmpleado);
	
	Empleado createEmpleado(EmpleadoDto empleado, Long idUsuario);
	
	Empleado deleteEmpleado(Long idEmpleado);
	
	Empleado updateEmpleado(Long idEmpleado, EmpleadoDto empleado);
}
