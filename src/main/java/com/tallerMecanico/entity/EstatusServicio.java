package com.tallerMecanico.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "estatusServicio")
public class EstatusServicio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idEstatusServicio;
	private String estatusServicio;
	
	public EstatusServicio() {
		super();
	}

	public EstatusServicio(long idEstatusServicio, String estatusServicio) {
		super();
		this.idEstatusServicio = idEstatusServicio;
		this.estatusServicio = estatusServicio;
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
	
}
