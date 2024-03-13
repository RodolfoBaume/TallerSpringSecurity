package com.tallerMecanico.dto;

import com.tallerMecanico.entity.Rol;

public record RolDto(Long idRol, String nombre) {
	public RolDto(Rol rol) {
		this(rol.getIdRol(), rol.getNombre());
	}
}
