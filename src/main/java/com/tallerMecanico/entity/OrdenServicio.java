package com.tallerMecanico.entity;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "ordenesServicios")
public class OrdenServicio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idOrdenServicio;
	private Date fechaOrden;
	private String falla;
	private String kilometraje;
	private String observaciones;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "estatusServicioId")
	private EstatusServicio estatusServicio;
	@OneToOne(mappedBy = "ordenServicio")
	private Factura factura;
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vehiculoId")
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idVehiculo")
	private Vehiculo vehiculo;	
	@OneToMany(mappedBy = "ordenServicio", orphanRemoval = true)
    private List<DetalleOrdenServicio> detalleOrdenServicios = new ArrayList<>();
	@Column(columnDefinition = "TEXT") // tipo text
	private String comentarios;
	@ManyToOne
	@JoinColumn(name = "empleadoId")
	private Empleado empleado;

	public OrdenServicio() {
		super();
	}

	public OrdenServicio(long idOrdenServicio, Date fechaOrden, String falla, String kilometraje, String observaciones,
			EstatusServicio estatusServicio, Factura factura, Vehiculo vehiculo,
			List<DetalleOrdenServicio> detalleOrdenServicios, String comentarios, Empleado empleado) {
		super();
		this.idOrdenServicio = idOrdenServicio;
		this.fechaOrden = fechaOrden;
		this.falla = falla;
		this.kilometraje = kilometraje;
		this.observaciones = observaciones;
		this.estatusServicio = estatusServicio;
		this.factura = factura;
		this.vehiculo = vehiculo;
		this.detalleOrdenServicios = detalleOrdenServicios;
		this.comentarios = comentarios;
		this.empleado = empleado;
	}

	public long getIdOrdenServicio() {
		return idOrdenServicio;
	}

	public void setIdOrdenServicio(long idOrdenServicio) {
		this.idOrdenServicio = idOrdenServicio;
	}

	public Date getFechaOrden() {
		return fechaOrden;
	}

	public void setFechaOrden(Date fechaOrden) {
		this.fechaOrden = fechaOrden;
	}

	public String getFalla() {
		return falla;
	}

	public void setFalla(String falla) {
		this.falla = falla;
	}

	public String getKilometraje() {
		return kilometraje;
	}

	public void setKilometraje(String kilometraje) {
		this.kilometraje = kilometraje;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public EstatusServicio getEstatusServicio() {
		return estatusServicio;
	}

	public void setEstatusServicio(EstatusServicio estatusServicio) {
		this.estatusServicio = estatusServicio;
	}

	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public List<DetalleOrdenServicio> getDetalleOrdenServicios() {
		return detalleOrdenServicios;
	}

	public void setDetalleOrdenServicios(List<DetalleOrdenServicio> detalleOrdenServicios) {
		this.detalleOrdenServicios = detalleOrdenServicios;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}
	
}
