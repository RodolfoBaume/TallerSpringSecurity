package com.tallerMecanico.dto;

import com.tallerMecanico.entity.Departamento;

public record DepartamentoDto(long idDepartamento, String departamento) {
	public DepartamentoDto(Departamento departamento) {
		this(departamento.getIdDepartamento(), departamento.getDepartamento());
	}
}
