package com.tallerMecanico.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "tipoMotor")
public class TipoMotor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idTipoMotor;
	private String tipoMotor;
	
	public TipoMotor() {
		super();
	}

	public TipoMotor(long idTipoMotor, String tipoMotor) {
		super();
		this.idTipoMotor = idTipoMotor;
		this.tipoMotor = tipoMotor;
	}

	public long getIdTipoMotor() {
		return idTipoMotor;
	}

	public void setIdTipoMotor(long idTipoMotor) {
		this.idTipoMotor = idTipoMotor;
	}

	public String getTipoMotor() {
		return tipoMotor;
	}

	public void setTipoMotor(String tipoMotor) {
		this.tipoMotor = tipoMotor;
	}
	
}
