package com.tallerMecanico.projection;

import java.util.Date;
import java.util.List;

public class FacturaClosedViewImpl implements IFacturaClosedView{
	private long idFactura;
	private Date fechaFactura;
	private double monto;
	private List<IDetalleFacturaProjection> detalleFactura;

    // Implementación de los getters y setters

	public long getIdFactura() {
		return idFactura;
	}

	public void setIdFactura(long idFactura) {
		this.idFactura = idFactura;
	}

	public Date getFechaFactura() {
		return fechaFactura;
	}

	public void setFechaFactura(Date fechaFactura) {
		this.fechaFactura = fechaFactura;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public List<IDetalleFacturaProjection> getDetalleFactura() {
		return detalleFactura;
	}

	public void setDetalleFactura(List<IDetalleFacturaProjection> detalleFactura) {
		this.detalleFactura = detalleFactura;
	}

	
	
}
