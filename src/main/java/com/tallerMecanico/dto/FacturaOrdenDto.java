package com.tallerMecanico.dto;

import java.util.Date;

public class FacturaOrdenDto {
	private long idFactura;
	private long ordenServicioId;
    private Date fechaFactura;
    private double monto;
    
    //private List<DetalleFacturaOrdenDto> detalles;
    
    public long getIdFactura() {
		return idFactura;
	}
	public void setIdFactura(long idFactura) {
		this.idFactura = idFactura;
	}
    
    public long getOrdenServicioId() {
		return ordenServicioId;
	}
	
	public void setOrdenServicioId(long ordenServicioId) {
		this.ordenServicioId = ordenServicioId;
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
	/*
	public List<DetalleFacturaOrdenDto> getDetalles() {
		return detalles;
	}
	public void setDetalles(List<DetalleFacturaOrdenDto> detalles) {
		this.detalles = detalles;
	}
    */
    
}
