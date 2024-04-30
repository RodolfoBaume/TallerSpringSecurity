package com.tallerMecanico.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "detalleFacturas")
public class DetalleFactura {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idDetalleFactura;
	private String descripcionServicio;
	private double costo;
	@ManyToOne
	private Factura factura;
	
	public DetalleFactura() {
		super();
	}
	
	public DetalleFactura(long idDetalleFactura, String descripcionServicio, double costo, Factura factura) {
		super();
		this.idDetalleFactura = idDetalleFactura;
		this.descripcionServicio = descripcionServicio;
		this.costo = costo;
		this.factura = factura;
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

	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}
	
}
