package com.tallerMecanico.service;

import java.util.List;

import com.tallerMecanico.dto.EmpleadoDto;
import com.tallerMecanico.entity.Empleado;

public interface IEmpleadoService {

	List<Empleado> findAll();
	
	Empleado findById(Long idEmpleado);
	
	Empleado createEmpleado(EmpleadoDto empleado);
	
	Empleado deleteEmpleado(Long idEmpleado);
	
	Empleado updateEmpleado(Long idEmpleado, EmpleadoDto empleado);
}
