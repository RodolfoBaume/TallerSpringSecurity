package com.tallerMecanico.dto;

import com.tallerMecanico.entity.DetalleFactura;
import com.tallerMecanico.entity.Factura;


public record DetalleFacturaDto(long idDetalleFactura, String descripcionServicio, double costo, Factura factura) {

	public DetalleFacturaDto(DetalleFactura detalleFactura) {
		this( detalleFactura.getIdDetalleFactura(), detalleFactura.getDescripcionServicio(), detalleFactura.getCosto(), detalleFactura.getFactura());
	}
}
