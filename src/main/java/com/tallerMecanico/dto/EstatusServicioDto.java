package com.tallerMecanico.dto;

import com.tallerMecanico.entity.EstatusServicio;

public record EstatusServicioDto(long idEstatusServicio, String estatusServicio) {

	public EstatusServicioDto(EstatusServicio estatusServicio) {
		this(estatusServicio.getIdEstatusServicio(), estatusServicio.getEstatusServicio());
	}
}
