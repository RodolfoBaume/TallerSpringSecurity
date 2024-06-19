package com.tallerMecanico.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "departamentos")
public class Departamento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idDepartamento;
	private String departamento;
	
	public Departamento() {
		super();
	}

	public Departamento(long idDepartamento, String departamento) {
		super();
		this.idDepartamento = idDepartamento;
		this.departamento = departamento;
	}

	public long getIdDepartamento() {
		return idDepartamento;
	}

	public void setIdDepartamento(long idDepartamento) {
		this.idDepartamento = idDepartamento;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

}
