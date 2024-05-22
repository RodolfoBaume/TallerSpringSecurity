package com.tallerMecanico.dto;

import com.tallerMecanico.entity.Marca;


public class ModeloOrdenDto {
	private long idModelo;
	private String modelo;
	private Marca marca;
	
	
	
	public ModeloOrdenDto() {
		super();
	}

	public ModeloOrdenDto(long idModelo, String modelo, Marca marca) {
		super();
		this.idModelo = idModelo;
		this.modelo = modelo;
		this.marca = marca;
	}
	
	public long getIdModelo() {
		return idModelo;
	}
	public void setIdModelo(long idModelo) {
		this.idModelo = idModelo;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public Marca getMarca() {
		return marca;
	}
	public void setMarca(Marca marca) {
		this.marca = marca;
	}
	
	
}
