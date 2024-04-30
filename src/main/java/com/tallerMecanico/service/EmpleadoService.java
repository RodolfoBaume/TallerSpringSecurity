package com.tallerMecanico.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tallerMecanico.dto.EmpleadoDto;
import com.tallerMecanico.entity.Empleado;
import com.tallerMecanico.entity.Usuario;
import com.tallerMecanico.repository.IEmpleadoRepository;
import com.tallerMecanico.repository.IUsuarioRepository;

@Service
public class EmpleadoService implements IEmpleadoService {

	@Autowired
	private IEmpleadoRepository empleadoRepository;
	
	@Autowired
	private IUsuarioRepository usuarioRepository;

	// Consulta todos
	@Transactional(readOnly = true)
	public List<Empleado> findAll() {
		return (List<Empleado>) empleadoRepository.findAll(Sort.by("idEmpleado"));
	}
	
	// consulta todos para paginación
	@Transactional(readOnly = true)
	public Page<Empleado> findAllPage(Pageable pageable) {
		return empleadoRepository.findAll(pageable);
	}

	// consulta por id
	@Transactional(readOnly = true)
	public Empleado findById(Long idEmpleado) {
		return empleadoRepository.findById(idEmpleado).orElse(null);
	}

	// Crear
	@Transactional
	public Empleado createEmpleado(EmpleadoDto empleado, Long idUsuario) {
		Empleado empleadoEntity = new Empleado();
		empleadoEntity.setNombre(empleado.nombre());
		empleadoEntity.setApellidoPaterno(empleado.apellidoPaterno());
		empleadoEntity.setApellidoMaterno(empleado.apellidoMaterno());
		empleadoEntity.setNss(empleado.nss());
		empleadoEntity.setCurp(empleado.curp());
		empleadoEntity.setRfc(empleado.rfc());
		empleadoEntity.setPuesto(empleado.puesto());
		empleadoEntity.setObservaciones(empleado.observaciones());

		// Asignar el idUsuario al empleado
		Usuario usuario = new Usuario(); // Debes cargar el usuario del repositorio utilizando su id, o bien asegurarte
											// de que el clienteDto contenga la información completa del usuario
		usuario.setIdUsuario(idUsuario);
		empleadoEntity.setUsuario(usuario);

		return empleadoRepository.save(empleadoEntity);
	}

	// Eliminar
	/*
	 * public Empleado deleteEmpleado(Long idEmpleado) {
	 * empleadoRepository.deleteById(idEmpleado); return null; }
	 */

	// Eliminar Empleado y usuario
	@Transactional
	public Empleado deleteEmpleado(Long idEmpleado) {
		// Buscar el empleado por su ID
		Empleado empleado = empleadoRepository.findById(idEmpleado).orElse(null);
		if (empleado == null) {
			// Si el empelado no existe, retornar o manejar el caso según corresponda
			return empleado;
		}

		// Obtener el usuario asociado al cliente
		Usuario usuario = empleado.getUsuario();
		if (usuario != null) {
			// Eliminar los roles asignados al usuario
			usuario.getRol().clear(); // Eliminar todos los roles asignados al usuario
			// Guardar el usuario para que se actualicen las relaciones
			usuarioRepository.save(usuario);
			// Eliminar el usuario
			usuarioRepository.delete(usuario);
		}

		// Ahora se puede eliminar el empleado
		empleadoRepository.delete(empleado);
		return empleado;
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
		empleadoEntity.setRfc(empleado.rfc());
		empleadoEntity.setPuesto(empleado.puesto());
		empleadoEntity.setObservaciones(empleado.observaciones());
		return empleadoRepository.save(empleadoEntity);
	}
}
