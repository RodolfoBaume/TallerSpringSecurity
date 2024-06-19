package com.tallerMecanico.dto;

import java.util.List;

import com.tallerMecanico.entity.Rol;
import com.tallerMecanico.entity.Usuario;


public record UsuarioDto(long idUsuario, String username, String password, List<Rol>rol) {
	public UsuarioDto(Usuario usuario) {
		this(usuario.getIdUsuario(), usuario.getUsername(), usuario.getPassword(), usuario.getRol());
	}
}
