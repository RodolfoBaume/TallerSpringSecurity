package com.tallerMecanico.dto;

import java.util.Date;

import com.tallerMecanico.entity.OrdenServicio;
import com.tallerMecanico.entity.StatusServicio;
import com.tallerMecanico.entity.Vehiculo;

public record OrdenServicioDto(long idOrdenServicio, Date fechaOrden, String falla, String kilometraje, String observaciones, StatusServicio statusServicio, Vehiculo vehiculo) {

	public OrdenServicioDto(OrdenServicio ordenServicio) {
		this(ordenServicio.getIdOrdenServicio(), ordenServicio.getFechaOrden(), ordenServicio.getFalla(), ordenServicio.getKilometraje(), ordenServicio.getObservaciones(), ordenServicio.getStatusServicio(), ordenServicio.getVehiculo());
	}

}
