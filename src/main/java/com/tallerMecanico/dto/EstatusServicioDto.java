package com.tallerMecanico.dto;

import com.tallerMecanico.entity.Departamento;
import com.tallerMecanico.entity.EstatusServicio;

public record EstatusServicioDto(long idEstatusServicio, String estatusServicio, Departamento departamento) {

	public EstatusServicioDto(EstatusServicio estatusServicio) {
		this(estatusServicio.getIdEstatusServicio(), estatusServicio.getEstatusServicio(), estatusServicio.getDepartamento());
	}
}
