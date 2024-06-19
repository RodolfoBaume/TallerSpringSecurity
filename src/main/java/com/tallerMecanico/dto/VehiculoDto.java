package com.tallerMecanico.dto;

import java.util.List;

import com.tallerMecanico.entity.Cliente;
import com.tallerMecanico.entity.Modelo;
import com.tallerMecanico.entity.OrdenServicio;
import com.tallerMecanico.entity.TipoMotor;
import com.tallerMecanico.entity.Vehiculo;


public record VehiculoDto(long idVehiculo, String vin, String matricula, Modelo modelo, int anioModelo, String color, TipoMotor tipoMotor, String imagen) {

	public VehiculoDto(Vehiculo vehiculo) {
		this(vehiculo.getIdVehiculo(), vehiculo.getVin(), vehiculo.getMatricula(), vehiculo.getModelo(), vehiculo.getAnioModelo(), vehiculo.getColor(), vehiculo.getTipoMotor(), vehiculo.getImagen());
	}
}