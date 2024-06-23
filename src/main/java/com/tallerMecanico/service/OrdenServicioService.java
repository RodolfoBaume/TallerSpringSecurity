package com.tallerMecanico.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tallerMecanico.dto.ModeloOrdenDto;
import com.tallerMecanico.dto.OrdenServicioDto;
import com.tallerMecanico.dto.OrdenServicioVehiculoDto;
import com.tallerMecanico.dto.VehiculoOrdenDto;
import com.tallerMecanico.entity.Modelo;
import com.tallerMecanico.entity.OrdenServicio;
import com.tallerMecanico.projection.IOrdenServicioDepto;
import com.tallerMecanico.projection.IOrdenServicioProjection;
import com.tallerMecanico.repository.IOrdenServicioRepository;

@Service
public class OrdenServicioService implements IOrdenServicioService {

	@Autowired
	private IOrdenServicioRepository ordenServicioRepository;

	@Autowired
	public OrdenServicioService(IOrdenServicioRepository ordenServicioRepository) {
		this.ordenServicioRepository = ordenServicioRepository;
	}
	
	
	public IOrdenServicioProjection getOrdenServicioById(Long idOrdenServicio) {
		return ordenServicioRepository.findProjectedById(idOrdenServicio);
	}

	public List<IOrdenServicioProjection> getAllOrdenesServicio() {
		return ordenServicioRepository.findAllProjected();
	}
	

	// Consulta todos
	@Transactional(readOnly = true)
	public List<OrdenServicio> findAll() {
		return (List<OrdenServicio>) ordenServicioRepository.findAll(Sort.by("idOrdenServicio"));
	}

	// consulta con vehiculo
	@Transactional(readOnly = true)
	public OrdenServicio buscarOrdenServicioConVehiculo(Long id) {
		return ordenServicioRepository.findByIdWithVehiculo(id);
	}

	@Transactional(readOnly = true)
	public List<OrdenServicio> buscarTodosConVehiculo() {
		return ordenServicioRepository.findAllWithVehiculo();
	}

	// consulta todos para paginación
	@Transactional(readOnly = true)
	public Page<OrdenServicio> findAllPage(Pageable pageable) {
		return ordenServicioRepository.findAll(pageable);
	}

	// consulta todos para paginación por DTO
	@Transactional(readOnly = true)
	public Page<OrdenServicioVehiculoDto> findAllPageDto(Pageable pageable) {
		Page<OrdenServicio> pageOrdenesServicio = ordenServicioRepository.findAll(pageable);
		return pageOrdenesServicio.map(ordenServicio -> {
			OrdenServicioVehiculoDto dto = new OrdenServicioVehiculoDto();
			dto.setIdOrdenServicio(ordenServicio.getIdOrdenServicio());
			dto.setFechaOrden(ordenServicio.getFechaOrden());
			dto.setFalla(ordenServicio.getFalla());
			dto.setKilometraje(ordenServicio.getKilometraje());
			dto.setEstatusServicio(ordenServicio.getEstatusServicio());
			dto.setObservaciones(ordenServicio.getObservaciones());
			dto.setVehiculo(new VehiculoOrdenDto(
					ordenServicio.getVehiculo().getIdVehiculo(),
					ordenServicio.getVehiculo().getVin(), 
					ordenServicio.getVehiculo().getMatricula(),
					convertToModeloDto(ordenServicio.getVehiculo().getModelo()),
					ordenServicio.getVehiculo().getAnioModelo(),
					ordenServicio.getVehiculo().getColor(), 
					ordenServicio.getVehiculo().getTipoMotor(),
					ordenServicio.getVehiculo().getImagen()));
			dto.setComentarios(ordenServicio.getComentarios());
			dto.setEmpleado(ordenServicio.getEmpleado());

			return dto;
		});
	}

	
	private ModeloOrdenDto convertToModeloDto(Modelo modelo) {
        if (modelo == null) {
            return null;
        }
        ModeloOrdenDto dto = new ModeloOrdenDto();
        dto.setIdModelo(modelo.getIdModelo());
        dto.setModelo(modelo.getModelo());
        dto.setMarca(modelo.getMarca());
        return dto;
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

	public List<OrdenServicio> obtenerPorEstatusServicio(String estatus) {
		return ordenServicioRepository.findByEstatusServicio(estatus);
	}

	public List<OrdenServicioVehiculoDto> getAllOrdenesServicioDTO() {
		List<OrdenServicio> ordenesServicio = ordenServicioRepository.findAll();

		List<OrdenServicioVehiculoDto> ordenesServicioDTO = new ArrayList<>();

		for (OrdenServicio ordenServicio : ordenesServicio) {
			OrdenServicioVehiculoDto ordenServicioDTO = new OrdenServicioVehiculoDto();
			ordenServicioDTO.setIdOrdenServicio(ordenServicio.getIdOrdenServicio());
			ordenServicioDTO.setFechaOrden(ordenServicio.getFechaOrden());
			ordenServicioDTO.setFalla(ordenServicio.getFalla());
			ordenServicioDTO.setKilometraje(ordenServicio.getKilometraje());
			ordenServicioDTO.setEstatusServicio(ordenServicio.getEstatusServicio());
			ordenServicioDTO.setObservaciones(ordenServicio.getObservaciones());
			// Aquí puedes setear los datos del vehículo manualmente
			ordenServicioDTO.setVehiculo(new VehiculoOrdenDto(
					ordenServicio.getVehiculo().getIdVehiculo(),
					ordenServicio.getVehiculo().getVin(), 
					ordenServicio.getVehiculo().getMatricula(),
					convertToModeloDto(ordenServicio.getVehiculo().getModelo()),
					ordenServicio.getVehiculo().getAnioModelo(),
					ordenServicio.getVehiculo().getColor(), 
					ordenServicio.getVehiculo().getTipoMotor(),
					ordenServicio.getVehiculo().getImagen()));
			ordenServicioDTO.setComentarios(ordenServicio.getComentarios());
			// ordenServicioDTO.setEmpleadoNombre(ordenServicio.getEmpleado().getNombre());
			// // Ejemplo de obtener el nombre del empleado

			ordenesServicioDTO.add(ordenServicioDTO);
		}

		return ordenesServicioDTO;
	}
	
	//OrdenServicio por departamento
	
	public List<OrdenServicio> getOrdenesByDepartamento(Long idDepartamento) {
        return ordenServicioRepository.findByDepartamentoId1(idDepartamento);
    }
    
	
	public List<IOrdenServicioDepto> getOrdenesServicioByDepartamento(Long idDepartamento) {
        return ordenServicioRepository.findByDepartamentoId(idDepartamento);
    }

}
