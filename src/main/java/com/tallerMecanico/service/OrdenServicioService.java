package com.tallerMecanico.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tallerMecanico.dto.OrdenServicioDto;
import com.tallerMecanico.entity.OrdenServicio;
import com.tallerMecanico.projection.IDetalleOrdenServicioProjection;
import com.tallerMecanico.projection.IOrdenServicioDepto;
import com.tallerMecanico.projection.IOrdenServicioProjection;
import com.tallerMecanico.projection.IOrdenServicioSinDetalle;
import com.tallerMecanico.projection.OrdenServicioProjectionImpl;
import com.tallerMecanico.repository.IDetalleOrdenServicioRepository;
import com.tallerMecanico.repository.IOrdenServicioRepository;

@Service
public class OrdenServicioService implements IOrdenServicioService {

	@Autowired
	private IOrdenServicioRepository ordenServicioRepository;
	@Autowired
	private IDetalleOrdenServicioRepository detalleOrdenServicioRepository;
	/*
	 * @Autowired public OrdenServicioService(IOrdenServicioRepository
	 * ordenServicioRepository) { this.ordenServicioRepository =
	 * ordenServicioRepository; }
	 */

	public OrdenServicioService(IOrdenServicioRepository ordenServicioRepository,
			IDetalleOrdenServicioRepository detalleOrdenServicioRepository) {
		this.ordenServicioRepository = ordenServicioRepository;
		this.detalleOrdenServicioRepository = detalleOrdenServicioRepository;
	}

	public List<IOrdenServicioSinDetalle> getAllOrdenesServicio() {
		return ordenServicioRepository.findAllProjected();
	}
	
	
	@Transactional(readOnly = true)
	public Slice<IOrdenServicioSinDetalle> getAllOrdenesServicio(Pageable pageable) {
	    return ordenServicioRepository.findAllProjected(pageable);
	}
	
	

	public IOrdenServicioProjection getOrdenServicioById(long ordenServicioId) {
		OrdenServicio ordenServicio = ordenServicioRepository.findById(ordenServicioId)
				.orElseThrow();
		List<IDetalleOrdenServicioProjection> detalles = detalleOrdenServicioRepository
				.findByOrdenServicio(ordenServicio);
		return new OrdenServicioProjectionImpl(ordenServicio, detalles);
	}


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
	 * @Transactional(readOnly = true) public OrdenServicio findById(Long
	 * idOrdenServicio) { OrdenServicio ordenServicio =
	 * ordenServicioRepository.findById(idOrdenServicio) .orElseThrow(() -> new
	 * NoSuchElementException("Orden de Servicio no encontrada con el ID: " +
	 * idOrdenServicio)); // Cargar explícitamente el vehículo para evitar que sea
	 * nulo en la serialización ordenServicio.getVehiculo(); // Esto carga el
	 * vehículo asociado a la orden de servicio return ordenServicio; }
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
		System.out.println(ordenServicio.toString());
		return ordenServicioRepository.save(ordenServicioEntity);
		// return new OrdenServicio();
	}

	// Eliminar
	public OrdenServicio deleteOrdenServicio(Long idOrdenServicio) {
		ordenServicioRepository.deleteById(idOrdenServicio);
		return null;
	}

	// Modificar
	@Transactional
	public OrdenServicio updateOrdenServicio(Long idOrdenServicio, OrdenServicioDto ordenServicio) {
		OrdenServicio ordenServicioEntity = ordenServicioRepository.findById(idOrdenServicio).orElseThrow(
				() -> new NoSuchElementException("Orden de Servicio no encontrada con el ID: " + idOrdenServicio));
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

	@Transactional
	public List<IOrdenServicioDepto> obtenerPorEstatusServicio(String estatus) {
		List<IOrdenServicioDepto> ordenes = null;

		try{
			ordenes = ordenServicioRepository.findByEstatusServicio(estatus);
		}catch(DataAccessException e){
			System.out.println( e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			System.out.println("ERROR SERVICIO>>>>>>>>");
		}
		return ordenes;
	}
	
	
	
	// paginacion por estatusServicio
	@Transactional
	public Page<IOrdenServicioDepto> obtenerPorEstatusServicio(@Param("estatus") String estatus, Pageable pageable){
		return ordenServicioRepository.findByEstatusServicio(estatus, pageable);
	}  


	// OrdenServicio por departamento
	public List<IOrdenServicioDepto> getOrdenesServicioByDepartamento(Long idDepartamento) {
		List<IOrdenServicioDepto> lista = ordenServicioRepository.findByDepartamentoId(idDepartamento);
		System.out.println(">>>>>>>>>>>>>>>>>> " + lista.toString());
		return lista;
	}
	
	// paginacion OrdenesServicio por departamento
	public Page<IOrdenServicioDepto> getOrdenesServicioByDepartamento(Long idDepartamento, Pageable pageable){
		return ordenServicioRepository.findByDepartamentoId(idDepartamento, pageable);
	}

}
