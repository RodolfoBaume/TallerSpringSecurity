package com.tallerMecanico.dto;


import com.tallerMecanico.entity.Cliente;
import com.tallerMecanico.entity.TipoMotor;


public class VehiculoOrdenDto {
	private long idVehiculo;
	private String vin;
	private String matricula;
	//@JsonIgnore
	private ModeloOrdenDto modelo;
	private int anioModelo;
	private String color;
	private TipoMotor tipoMotor;
	private String imagen;
	private Cliente cliente;

	public VehiculoOrdenDto(long idVehiculo, String vin, String matricula, ModeloOrdenDto modelo, int anioModelo,
			String color, TipoMotor tipoMotor, String imagen, Cliente cliente) {
		super();
		this.idVehiculo = idVehiculo;
		this.vin = vin;
		this.matricula = matricula;
		this.modelo = modelo;
		this.anioModelo = anioModelo;
		this.color = color;
		this.tipoMotor = tipoMotor;
		this.imagen = imagen;
		this.cliente = cliente;
	}


	public long getIdVehiculo() {
		return idVehiculo;
	}


	public void setIdVehiculo(long idVehiculo) {
		this.idVehiculo = idVehiculo;
	}


	public String getVin() {
		return vin;
	}


	public void setVin(String vin) {
		this.vin = vin;
	}


	public String getMatricula() {
		return matricula;
	}


	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}


	public ModeloOrdenDto getModelo() {
		return modelo;
	}


	public void setModelo(ModeloOrdenDto modelo) {
		this.modelo = modelo;
	}


	public int getAnioModelo() {
		return anioModelo;
	}


	public void setAnioModelo(int anioModelo) {
		this.anioModelo = anioModelo;
	}


	public String getColor() {
		return color;
	}


	public void setColor(String color) {
		this.color = color;
	}


	public TipoMotor getTipoMotor() {
		return tipoMotor;
	}


	public void setTipoMotor(TipoMotor tipoMotor) {
		this.tipoMotor = tipoMotor;
	}


	public String getImagen() {
		return imagen;
	}


	public void setImagen(String imagen) {
		this.imagen = imagen;
	}


	public Cliente getCliente() {
		return cliente;
	}


	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

    
}
