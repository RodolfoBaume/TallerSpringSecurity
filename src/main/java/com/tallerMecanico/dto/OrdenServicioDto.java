package com.tallerMecanico.dto;

import java.util.Date;

import com.tallerMecanico.entity.EstatusServicio;
import com.tallerMecanico.entity.Factura;
import com.tallerMecanico.entity.OrdenServicio;
import com.tallerMecanico.entity.Vehiculo;

public record OrdenServicioDto(long idOrdenServicio, Date fechaOrden, String falla, String kilometraje, String observaciones, EstatusServicio estatusServicio, Factura factura, Vehiculo vehiculo) {

	public OrdenServicioDto(OrdenServicio ordenServicio) {
		this(ordenServicio.getIdOrdenServicio(), ordenServicio.getFechaOrden(), ordenServicio.getFalla(), ordenServicio.getKilometraje(), ordenServicio.getObservaciones(), ordenServicio.getEstatusServicio(), ordenServicio.getFactura(), ordenServicio.getVehiculo());
	}
}
