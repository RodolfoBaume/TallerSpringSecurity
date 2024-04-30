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
import com.tallerMecanico.repository.IVehiculoRepository;

@Service
public class VehiculoService implements IVehiculoService{

	@Autowired
	private IVehiculoRepository vehiculoRepository;

	// Consulta todos
	@Transactional(readOnly = true)
	public List<Vehiculo> findAll() {
		return (List<Vehiculo>) vehiculoRepository.findAll(Sort.by("idVehiculo"));
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
		vehiculoEntity.setOrdenServicio(vehiculo.ordenServicio());
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
		vehiculoEntity.setOrdenServicio(vehiculo.ordenServicio());
		vehiculoEntity.setCliente(vehiculo.cliente());
		return vehiculoRepository.save(vehiculoEntity);
	}
}
