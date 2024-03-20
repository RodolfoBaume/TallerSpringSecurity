package com.tallerMecanico.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tallerMecanico.dto.EmpleadoDto;
import com.tallerMecanico.entity.Empleado;
import com.tallerMecanico.repository.IEmpleadoRepository;

@Service
public class EmpleadoService implements IEmpleadoService{

	@Autowired
	private IEmpleadoRepository empleadoRepository;

	// Consulta todos
	@Transactional(readOnly = true)
	public List<Empleado> findAll() {
		return (List<Empleado>) empleadoRepository.findAll(Sort.by("idEmpleado"));
	}

	// consulta por id
	@Transactional(readOnly = true)
	public Empleado findById(Long idEmpleado) {
		return empleadoRepository.findById(idEmpleado).orElse(null);
	}

	// Crear
	@Transactional
	public Empleado createEmpleado(EmpleadoDto empleado) {
		Empleado empleadoEntity = new Empleado();
		empleadoEntity.setNombre(empleado.nombre());
		empleadoEntity.setApellidoPaterno(empleado.apellidoPaterno());
		empleadoEntity.setApellidoMaterno(empleado.apellidoMaterno());
		empleadoEntity.setNss(empleado.nss());
		empleadoEntity.setCurp(empleado.curp());
		empleadoEntity.setRFC(empleado.RFC());
		empleadoEntity.setPuesto(empleado.puesto());
		empleadoEntity.setObservaciones(empleado.observaciones());
		return empleadoRepository.save(empleadoEntity);
	}

	// Eliminar
	public Empleado deleteEmpleado(Long idEmpleado) {
		empleadoRepository.deleteById(idEmpleado);
		return null;
	}

	// Modificar
	@Transactional
	public Empleado updateEmpleado(Long idEmpleado, EmpleadoDto empleado) {
		Empleado empleadoEntity = empleadoRepository.findById(idEmpleado)
				.orElseThrow(() -> new NoSuchElementException("Empleado no encontrado con el ID: " + idEmpleado));
		empleadoEntity.setNombre(empleado.nombre());
		empleadoEntity.setApellidoPaterno(empleado.apellidoPaterno());
		empleadoEntity.setApellidoMaterno(empleado.apellidoMaterno());
		empleadoEntity.setNss(empleado.nss());
		empleadoEntity.setCurp(empleado.curp());
		empleadoEntity.setRFC(empleado.RFC());
		empleadoEntity.setPuesto(empleado.puesto());
		empleadoEntity.setObservaciones(empleado.observaciones());
		return empleadoRepository.save(empleadoEntity);
	}
}
