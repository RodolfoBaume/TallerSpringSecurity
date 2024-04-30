package com.tallerMecanico.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "detalleOrdenServicios")
public class DetalleOrdenServicio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idDetalleOrdenServicio;
	private String descripcionServicio;
	@ManyToOne
	@JoinColumn(name = "ordenServicioId")
	private OrdenServicio ordenServicio;
	
	public DetalleOrdenServicio() {
		super();
	}
	
	public DetalleOrdenServicio(long idDetalleOrdenServicio, String descripcionServicio, OrdenServicio ordenServicio) {
		super();
		this.idDetalleOrdenServicio = idDetalleOrdenServicio;
		this.descripcionServicio = descripcionServicio;
		this.ordenServicio = ordenServicio;
	}

	public long getIdDetalleOrdenServicio() {
		return idDetalleOrdenServicio;
	}

	public void setIdDetalleOrdenServicio(long idDetalleOrdenServicio) {
		this.idDetalleOrdenServicio = idDetalleOrdenServicio;
	}

	public String getDescripcionServicio() {
		return descripcionServicio;
	}

	public void setDescripcionServicio(String descripcionServicio) {
		this.descripcionServicio = descripcionServicio;
	}

	public OrdenServicio getOrdenServicio() {
		return ordenServicio;
	}

	public void setOrdenServicio(OrdenServicio ordenServicio) {
		this.ordenServicio = ordenServicio;
	}

}
