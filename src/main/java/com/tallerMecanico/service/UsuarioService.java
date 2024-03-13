package com.tallerMecanico.service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tallerMecanico.dto.UsuarioDto;
import com.tallerMecanico.entity.Rol;
import com.tallerMecanico.entity.Usuario;
import com.tallerMecanico.repository.IRolRepository;
import com.tallerMecanico.repository.IUsuarioRepository;


@Service
public class UsuarioService implements IUsuarioService {

	@Autowired
	private IUsuarioRepository usuarioRepository;
	private IRolRepository rolRepository;
	
	@Autowired
    public UsuarioService(IUsuarioRepository usuarioRepository, IRolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
    }

	// Consulta todos
	@Transactional(readOnly = true)
	public List<Usuario> findAll() {
		return (List<Usuario>) usuarioRepository.findAll(Sort.by("idUsuario"));
	}

	// consulta por id
	@Transactional(readOnly = true)
	public Usuario findById(Long idUsuario) {
		return usuarioRepository.findById(idUsuario).orElse(null);
	}

	/*
	// Crear
	@Transactional
	public Usuario createUsuario(UsuarioDto usuario) {
		Usuario usuarioEntity = new Usuario();
		usuarioEntity.setIdUsuario(usuario.idUsuario());
		usuarioEntity.setEmail(usuario.email());
		usuarioEntity.setPassword(usuario.password());
		return usuarioRepository.save(usuarioEntity);
	}
*/
	// register
	public ResponseEntity<String> registrarUsuario(UsuarioDto dtoRegistro, String role) {
		if (usuarioRepository.existsByEmail(dtoRegistro.email())) {
			return new ResponseEntity<>("El usuario ya existe, intenta con otro", HttpStatus.BAD_REQUEST);
		}

		Usuario usuario = new Usuario();
		usuario.setEmail(dtoRegistro.email()); 
		usuario.setPassword(dtoRegistro.password());
		//usuarios.setPassword(passwordEncoder.encode(dtoRegistro.getPassword()));

		Rol roles = rolRepository.findByNombre(role).orElse(null);

		if (roles == null) {
			return new ResponseEntity<>("Rol no válido", HttpStatus.BAD_REQUEST);
		}

		usuario.setRol(Collections.singletonList(roles)); 
		usuarioRepository.save(usuario);

		return new ResponseEntity<>("Registro exitoso", HttpStatus.OK);
	}

	
	
	// Eliminar
	public Usuario deleteUsuario(Long idUsuario) {
		usuarioRepository.deleteById(idUsuario);
		return null;
	}

	// Modificar
	@Transactional
	public Usuario updateUsuario(Long idUsuario, UsuarioDto usuario) {
		Usuario usuarioEntity = usuarioRepository.findById(idUsuario)
				.orElseThrow(() -> new NoSuchElementException("Usuario no encontrado con el ID: " + idUsuario));
		usuarioEntity.setIdUsuario(usuario.idUsuario());
		usuarioEntity.setEmail(usuario.email());
		usuarioEntity.setPassword(usuario.password());
		return usuarioRepository.save(usuarioEntity);
	}


}
