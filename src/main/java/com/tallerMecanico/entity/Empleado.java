package com.tallerMecanico.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "empleados")
public class Empleado {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idEmpleado;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private long nss;
	private String curp;
	private String rfc;
	private String puesto;
	private String observaciones;
	@OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
	
	public Empleado() {
		super();
	}

	public Empleado(long idEmpleado, String nombre, String apellidoPaterno, String apellidoMaterno, long nss,
			String curp, String rfc, String puesto, String observaciones, Usuario usuario) {
		super();
		this.idEmpleado = idEmpleado;
		this.nombre = nombre;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.nss = nss;
		this.curp = curp;
		this.rfc = rfc;
		this.puesto = puesto;
		this.observaciones = observaciones;
		this.usuario = usuario;
	}

	public long getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public long getNss() {
		return nss;
	}

	public void setNss(long nss) {
		this.nss = nss;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
