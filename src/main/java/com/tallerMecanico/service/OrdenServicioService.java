package com.tallerMecanico.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tallerMecanico.dto.OrdenServicioDto;
import com.tallerMecanico.entity.OrdenServicio;
import com.tallerMecanico.repository.IOrdenServicioRepository;

@Service
public class OrdenServicioService implements IOrdenServicioService {

	@Autowired
	private IOrdenServicioRepository ordenServicioRepository;

	// Consulta todos
	@Transactional(readOnly = true)
	public List<OrdenServicio> findAll() {
		return (List<OrdenServicio>) ordenServicioRepository.findAll(Sort.by("idOrdenServicio"));
	}
	
	// consulta todos para paginación
	@Transactional(readOnly = true)
	public Page<OrdenServicio> findAllPage(Pageable pageable) {
		return ordenServicioRepository.findAll(pageable);
	}

	// consulta por id
	@Transactional(readOnly = true)
	public OrdenServicio findById(Long idOrdenServicio) {
		return ordenServicioRepository.findById(idOrdenServicio).orElse(null);
	}
	
	/*
	@Transactional(readOnly = true)
	public OrdenServicio findById(Long idOrdenServicio) {
	    OrdenServicio ordenServicio = ordenServicioRepository.findById(idOrdenServicio)
	            .orElseThrow(() -> new NoSuchElementException("Orden de Servicio no encontrada con el ID: " + idOrdenServicio));
	    // Cargar explícitamente el vehículo para evitar que sea nulo en la serialización
	    ordenServicio.getVehiculo(); // Esto carga el vehículo asociado a la orden de servicio
	    return ordenServicio;
	}
*/
	
	// Crear
	@Transactional
	public OrdenServicio createOrdenServicio(OrdenServicioDto ordenServicio) {
		OrdenServicio ordenServicioEntity = new OrdenServicio();
		ordenServicioEntity.setFechaOrden(ordenServicio.fechaOrden());
		ordenServicioEntity.setFalla(ordenServicio.falla());
		ordenServicioEntity.setKilometraje(ordenServicio.kilometraje());
		ordenServicioEntity.setObservaciones(ordenServicio.observaciones());
		ordenServicioEntity.setEstatusServicio(ordenServicio.estatusServicio());
		ordenServicioEntity.setFactura(ordenServicio.factura());
		ordenServicioEntity.setVehiculo(ordenServicio.vehiculo());
		ordenServicioEntity.setComentarios(ordenServicio.comentarios());
		ordenServicioEntity.setEmpleado(ordenServicio.empleado());
		return ordenServicioRepository.save(ordenServicioEntity);
	}

	// Eliminar
	public OrdenServicio deleteOrdenServicio(Long idOrdenServicio) {
		ordenServicioRepository.deleteById(idOrdenServicio);
		return null;
	}

	// Modificar
	@Transactional
	public OrdenServicio updateOrdenServicio(Long idOrdenServicio, OrdenServicioDto ordenServicio) {
		OrdenServicio ordenServicioEntity = ordenServicioRepository.findById(idOrdenServicio)
				.orElseThrow(() -> new NoSuchElementException("Orden de Servicio no encontrada con el ID: " + idOrdenServicio));
		ordenServicioEntity.setFechaOrden(ordenServicio.fechaOrden());
		ordenServicioEntity.setFalla(ordenServicio.falla());
		ordenServicioEntity.setKilometraje(ordenServicio.kilometraje());
		ordenServicioEntity.setObservaciones(ordenServicio.observaciones());
		ordenServicioEntity.setEstatusServicio(ordenServicio.estatusServicio());
		ordenServicioEntity.setFactura(ordenServicio.factura());
		ordenServicioEntity.setVehiculo(ordenServicio.vehiculo());
		ordenServicioEntity.setComentarios(ordenServicio.comentarios());
		ordenServicioEntity.setEmpleado(ordenServicio.empleado());
		
		return ordenServicioRepository.save(ordenServicioEntity);
	}
}
