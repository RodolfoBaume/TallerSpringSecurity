package com.tallerMecanico.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tallerMecanico.entity.DetalleOrdenServicio;
import com.tallerMecanico.entity.Empleado;
import com.tallerMecanico.entity.EstatusServicio;
import com.tallerMecanico.entity.Factura;

public class OrdenServicioVehiculoDto {
	private long idOrdenServicio;
	private Date fechaOrden;
	private String falla;
	private String kilometraje;
	private String observaciones;
	private EstatusServicio estatusServicio;
	private Factura factura;
	private VehiculoOrdenDto vehiculo;	
    private List<DetalleOrdenServicio> detalleOrdenServicios = new ArrayList<>();
	private String comentarios;
	private Empleado empleado;
	
	public OrdenServicioVehiculoDto() {
		super();
	}

	public OrdenServicioVehiculoDto(long idOrdenServicio, Date fechaOrden, String falla, String kilometraje,
			String observaciones, EstatusServicio estatusServicio, Factura factura, VehiculoOrdenDto vehiculo,
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


	public VehiculoOrdenDto getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(VehiculoOrdenDto vehiculo) {
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
