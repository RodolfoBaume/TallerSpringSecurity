package com.tallerMecanico.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "facturas")
public class Factura {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idFactura;
	private Date fechaFactura;
	private double monto;
	@OneToOne
	@JoinColumn(name="ordenServicio_id")
	private OrdenServicio ordenServicio;
	@OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleFactura> detalleFacturas = new ArrayList<>();
	
	public Factura() {
		super();
	}

	public Factura(long idFactura, Date fechaFactura, double monto, OrdenServicio ordenServicio,
			List<DetalleFactura> detalleFacturas) {
		super();
		this.idFactura = idFactura;
		this.fechaFactura = fechaFactura;
		this.monto = monto;
		this.ordenServicio = ordenServicio;
		this.detalleFacturas = detalleFacturas;
	}

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

	public OrdenServicio getOrdenServicio() {
		return ordenServicio;
	}

	public void setOrdenServicio(OrdenServicio ordenServicio) {
		this.ordenServicio = ordenServicio;
	}

	public List<DetalleFactura> getDetalleFacturas() {
		return detalleFacturas;
	}

	public void setDetalleFacturas(List<DetalleFactura> detalleFacturas) {
		this.detalleFacturas = detalleFacturas;
	}

}
