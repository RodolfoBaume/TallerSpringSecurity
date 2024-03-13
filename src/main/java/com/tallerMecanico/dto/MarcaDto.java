package com.tallerMecanico.dto;

import com.tallerMecanico.entity.Marca;

public record MarcaDto(long idMarca, String marca) {

	public MarcaDto(Marca marca) {
		this(marca.getIdMarca(), marca.getMarca());
	}
}
