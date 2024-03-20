package com.tallerMecanico.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.tallerMecanico.dto.RegistroResponseDto;
import com.tallerMecanico.dto.UsuarioDto;
import com.tallerMecanico.entity.Usuario;

public interface IUsuarioService {

	List<Usuario> findAll();
	
	Usuario findById(Long idUsuario);
	
	ResponseEntity<RegistroResponseDto> registrarUsuario(UsuarioDto dtoRegistro, String role);
	
	Usuario deleteUsuario(Long idUsuario);
	
	Usuario updateUsuario(Long idUsuario, UsuarioDto usuario);
}
