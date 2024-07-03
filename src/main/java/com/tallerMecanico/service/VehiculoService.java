package com.tallerMecanico.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tallerMecanico.dto.VehiculoDto;
import com.tallerMecanico.entity.Vehiculo;
import com.tallerMecanico.projection.IVehiculoConOrdenClosedView;
import com.tallerMecanico.repository.IVehiculoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class VehiculoService implements IVehiculoService {

	@Autowired
	private IVehiculoRepository vehiculoRepository;

	// Consulta todos
	@Transactional(readOnly = true)
	public List<Vehiculo> findAll() {
		return (List<Vehiculo>) vehiculoRepository.findAll(Sort.by("idVehiculo"));
	}

	// vehiculos con orden de servicio
	/*
	 * @Transactional(readOnly = true) public List<Vehiculo>
	 * obtenerTodosLosVehiculosConOrdenServicio() { return
	 * vehiculoRepository.findAllWithOrdenServicio(); }
	 */

	@Transactional
	public List<IVehiculoConOrdenClosedView> findBy() {
		return vehiculoRepository.findBy();
	}

	@Transactional(readOnly = true)
	public Page<IVehiculoConOrdenClosedView> findBy(Pageable pageable) {
		return vehiculoRepository.findBy(pageable);
	}

	// vehiculo por id
	// @Transactional
	public IVehiculoConOrdenClosedView getVehiculoById(long id) {
		return vehiculoRepository.findByIdVehiculo(id)
				.orElseThrow(() -> new EntityNotFoundException("Vehiculo not found with id " + id));
	}
	
	// para admin o empleados
	@Transactional(readOnly = true)
    public IVehiculoConOrdenClosedView findByIdVehiculo(Long idVehiculo) {
        return vehiculoRepository.findByIdVehiculo(idVehiculo)
        		.orElseThrow(() -> new EntityNotFoundException("Vehiculo not found with id " + idVehiculo));
    }

	//para clientes
	@Transactional(readOnly = true)
	public IVehiculoConOrdenClosedView findByIdVehiculoAndClienteId(Long idVehiculo, Long idCliente) {
	    return vehiculoRepository.findByIdVehiculoAndClienteId(idVehiculo, idCliente)
	            .orElseThrow(() -> new EntityNotFoundException("Vehiculo not found with id " + idVehiculo + " for cliente " + idCliente));
	}
	
	// consulta todos para paginación
	@Transactional(readOnly = true)
	public Page<Vehiculo> findAllPage(Pageable pageable) {
		return vehiculoRepository.findAll(pageable);
	}

	// consulta por id
	@Transactional(readOnly = true)
	public Vehiculo findById(Long idVehiculo) {
		return vehiculoRepository.findById(idVehiculo).orElse(null);
	}
	

	// Crear
	@Transactional
	public Vehiculo createVehiculo(VehiculoDto vehiculo) {
		Vehiculo vehiculoEntity = new Vehiculo();
		vehiculoEntity.setVin(vehiculo.vin());
		vehiculoEntity.setMatricula(vehiculo.matricula());
		vehiculoEntity.setAnioModelo(vehiculo.anioModelo());
		vehiculoEntity.setColor(vehiculo.color());
		vehiculoEntity.setTipoMotor(vehiculo.tipoMotor());
		vehiculoEntity.setImagen(vehiculo.imagen());
		vehiculoEntity.setModelo(vehiculo.modelo());
		// vehiculoEntity.setOrdenServicio(vehiculo.ordenServicio());
		vehiculoEntity.setCliente(vehiculo.cliente());
		return vehiculoRepository.save(vehiculoEntity);
	}

	// Eliminar
	public Vehiculo deleteVehiculo(Long idVehiculo) {
		vehiculoRepository.deleteById(idVehiculo);
		return null;
	}

	// Modificar
	@Transactional
	public Vehiculo updateVehiculo(Long idVehiculo, VehiculoDto vehiculo) {
		Vehiculo vehiculoEntity = vehiculoRepository.findById(idVehiculo)
				.orElseThrow(() -> new NoSuchElementException("Vehiculo no encontrado con el ID: " + idVehiculo));
		vehiculoEntity.setVin(vehiculo.vin());
		vehiculoEntity.setMatricula(vehiculo.matricula());
		vehiculoEntity.setAnioModelo(vehiculo.anioModelo());
		vehiculoEntity.setColor(vehiculo.color());
		vehiculoEntity.setTipoMotor(vehiculo.tipoMotor());
		vehiculoEntity.setImagen(vehiculo.imagen());
		vehiculoEntity.setModelo(vehiculo.modelo());
		// vehiculoEntity.setOrdenServicio(vehiculo.ordenServicio());
		// vehiculoEntity.setCliente(vehiculo.cliente());
		return vehiculoRepository.save(vehiculoEntity);
	}
	
	public List<IVehiculoConOrdenClosedView> getVehiculosByOrdenServicioEstatus(String estatus) {
        return vehiculoRepository.findVehiculosByOrdenServicioEstatus(estatus);
    }

}
