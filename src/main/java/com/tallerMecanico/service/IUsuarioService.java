package com.tallerMecanico.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.tallerMecanico.dto.UsuarioDto;
import com.tallerMecanico.entity.Usuario;

public interface IUsuarioService {

	List<Usuario> findAll();
	
	Usuario findById(Long idUsuario);
	
	// Usuario createUsuario(UsuarioDto usuario);
	ResponseEntity<String> registrarUsuario(UsuarioDto dtoRegistro, String role);
	
	Usuario deleteUsuario(Long idUsuario);
	
	Usuario updateUsuario(Long idUsuario, UsuarioDto usuario);
}
