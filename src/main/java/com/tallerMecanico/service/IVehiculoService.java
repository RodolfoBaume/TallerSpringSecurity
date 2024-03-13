package com.tallerMecanico.service;

import java.util.List;

import com.tallerMecanico.dto.VehiculoDto;
import com.tallerMecanico.entity.Vehiculo;

public interface IVehiculoService {

	List<Vehiculo> findAll();
	
	Vehiculo findById(Long idVehiculo);
	
	Vehiculo createVehiculo(VehiculoDto vehiculo);
	
	Vehiculo deleteVehiculo(Long idVehiculo);
	
	Vehiculo updateVehiculo(Long idVehiculo, VehiculoDto vehiculo);
	
}
