package com.tallerMecanico.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="modelos")
public class Modelo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idModelo;
	private String modelo;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "marcaId")
	private Marca marca;

	public Modelo() {
		super();
	}

	public Modelo(long idModelo, String modelo, Marca marca) {
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
