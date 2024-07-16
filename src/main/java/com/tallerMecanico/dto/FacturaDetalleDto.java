package com.tallerMecanico.dto;

import java.util.Date;
import java.util.List;

public class FacturaDetalleDto {
	private Long idFactura;
    private Date fechaFactura;
    private double monto;
    private List<DetalleFacturasDto> detalles;
    
   
   public FacturaDetalleDto(Long idFactura, Date fechaFactura, double monto, List<DetalleFacturasDto> detalles) {
        this.idFactura = idFactura;
        this.fechaFactura = fechaFactura;
        this.monto = monto;
        this.detalles = detalles;
    }

	public Long getIdFactura() {
		return idFactura;
	}

	public void setIdFactura(Long idFactura) {
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

	public List<DetalleFacturasDto> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetalleFacturasDto> detalles) {
		this.detalles = detalles;
	}
    
    
}
