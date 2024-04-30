package com.tallerMecanico.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
@Table(name = "clientes")
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idCliente;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String domicilio;
	private String telefono;
	@OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
	@OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "clienteId")
	@JsonManagedReference
    private List<Vehiculo> vehiculos;
	
	
	public Cliente() {
		super();
	}


	public Cliente(long idCliente, String nombre, String apellidoPaterno, String apellidoMaterno, String domicilio,
			String telefono, Usuario usuario, List<Vehiculo> vehiculos) {
		super();
		this.idCliente = idCliente;
		this.nombre = nombre;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.domicilio = domicilio;
		this.telefono = telefono;
		this.usuario = usuario;
		this.vehiculos = vehiculos;
	}


	public long getIdCliente() {
		return idCliente;
	}


	public void setIdCliente(long idCliente) {
		this.idCliente = idCliente;
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


	public String getDomicilio() {
		return domicilio;
	}


	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}


	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public List<Vehiculo> getVehiculos() {
		return vehiculos;
	}


	public void setVehiculos(List<Vehiculo> vehiculos) {
		this.vehiculos = vehiculos;
	}

	
}
