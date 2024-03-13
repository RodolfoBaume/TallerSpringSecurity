package com.tallerMecanico.dto;

import com.tallerMecanico.entity.Marca;
import com.tallerMecanico.entity.Modelo;


public record ModeloDto(long idModelo, String modelo, Marca marca) {

	public ModeloDto(Modelo modelo) {
		this(modelo.getIdModelo(), modelo.getModelo(),modelo.getMarca());
	}
}
