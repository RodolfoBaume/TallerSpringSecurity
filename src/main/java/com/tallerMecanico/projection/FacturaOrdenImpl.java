package com.tallerMecanico.projection;

import java.util.Date;

import com.tallerMecanico.entity.Factura;

public class FacturaOrdenImpl implements IFacturaProjection {
	
	private Factura factura;
	
	public FacturaOrdenImpl(Factura factura) {
		this.factura = factura;
	}

	@Override
	public long getIdFactura() {
		return factura.getIdFactura();
	}

	@Override
	public Date getFechaFactura() {
		return factura.getFechaFactura();
	}

	@Override
	public double getMonto() {
		return factura.getMonto();
	}

}
