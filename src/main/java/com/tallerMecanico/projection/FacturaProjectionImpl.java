package com.tallerMecanico.projection;

import java.util.Date;
import java.util.List;

import com.tallerMecanico.entity.Factura;

public class FacturaProjectionImpl implements IFacturaProjection{
	
	private Factura factura;
	private List<IDetalleFacturaProjection> detallesFactura;
	
	public FacturaProjectionImpl (Factura factura, List<IDetalleFacturaProjection> detallesFactura) {
		this.factura = factura;
		this.detallesFactura = detallesFactura;
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

	@Override
	public List<IDetalleFacturaProjection> getDetalleFactura() {
		return detallesFactura;
	}

}
