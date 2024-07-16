package com.tallerMecanico.dto;

public class DetalleFacturasDto {
	private long idDetalleFactura;
    private String descripcionServicio;
    private double costo;
    private Long facturaId;

    public DetalleFacturasDto(long idDetalleFactura, String descripcionServicio, double costo) {
        this.idDetalleFactura = idDetalleFactura;
        this.descripcionServicio = descripcionServicio;
        this.costo = costo;
    }

	public long getIdDetalleFactura() {
		return idDetalleFactura;
	}

	public void setIdDetalleFactura(long idDetalleFactura) {
		this.idDetalleFactura = idDetalleFactura;
	}

	public String getDescripcionServicio() {
		return descripcionServicio;
	}

	public void setDescripcionServicio(String descripcionServicio) {
		this.descripcionServicio = descripcionServicio;
	}

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	public Long getFacturaId() {
		return facturaId;
	}

	public void setFacturaId(Long facturaId) {
		this.facturaId = facturaId;
	}
    
    
}
