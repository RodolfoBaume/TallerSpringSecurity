package com.tallerMecanico.dto;

public class DetalleFacturaOrdenDto {
	private String descripcionServicio;
    private double costo;
    
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
    
    
}
