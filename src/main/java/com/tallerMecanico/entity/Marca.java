package com.tallerMecanico.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "marcas")
public class Marca {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idMarca;
	private String marca;
	
	public Marca() {
		super();
	}

	public Marca(long idMarca, String marca) {
		super();
		this.idMarca = idMarca;
		this.marca = marca;
	}

	public long getIdMarca() {
		return idMarca;
	}

	public void setIdMarca(long idMarca) {
		this.idMarca = idMarca;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}
	
	
}
