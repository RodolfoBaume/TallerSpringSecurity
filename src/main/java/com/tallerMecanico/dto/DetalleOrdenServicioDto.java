package com.tallerMecanico.dto;

import com.tallerMecanico.entity.DetalleOrdenServicio;
import com.tallerMecanico.entity.OrdenServicio;


public record DetalleOrdenServicioDto(long idDetalleOrdenServicios, String descripcionServicio, OrdenServicio ordenServicio) {

	public DetalleOrdenServicioDto(DetalleOrdenServicio detalleOrdenServicio) {
		this(detalleOrdenServicio.getIdDetalleOrdenServicio(), detalleOrdenServicio.getDescripcionServicio(), detalleOrdenServicio.getOrdenServicio());
	}
}
