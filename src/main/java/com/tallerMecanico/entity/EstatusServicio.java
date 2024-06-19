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
@Table(name = "estatusServicio")
public class EstatusServicio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idEstatusServicio;
	private String estatusServicio;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "departamentoId")
	private Departamento departamento;
	
	public EstatusServicio() {
		super();
	}

	public EstatusServicio(long idEstatusServicio, String estatusServicio, Departamento departamento) {
		super();
		this.idEstatusServicio = idEstatusServicio;
		this.estatusServicio = estatusServicio;
		this.departamento = departamento;
	}

	public long getIdEstatusServicio() {
		return idEstatusServicio;
	}

	public void setIdEstatusServicio(long idEstatusServicio) {
		this.idEstatusServicio = idEstatusServicio;
	}

	public String getEstatusServicio() {
		return estatusServicio;
	}

	public void setEstatusServicio(String estatusServicio) {
		this.estatusServicio = estatusServicio;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

}
