package com.tallerMecanico.service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tallerMecanico.dto.RegistroResponseDto;
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
	private PasswordEncoder passwordEncoder;
	
	@Autowired
    public UsuarioService(IUsuarioRepository usuarioRepository, IRolRepository rolRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
		this.passwordEncoder = passwordEncoder; 
    }

	// Consulta todos
	@Transactional(readOnly = true)
	public List<Usuario> findAll() {
		return (List<Usuario>) usuarioRepository.findAll(Sort.by("idUsuario"));
	}
	
	// consulta todos para paginación
	@Transactional(readOnly = true)
	public Page<Usuario> findAllPage(Pageable pageable) {
		return usuarioRepository.findAll(pageable);
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
	public ResponseEntity<RegistroResponseDto> registrarUsuario(UsuarioDto dtoRegistro, String role) {
	    if (usuarioRepository.existsByUsername(dtoRegistro.username())) {
	        return new ResponseEntity<>(new RegistroResponseDto("El usuario ya existe, intenta con otro", null), HttpStatus.BAD_REQUEST);
	    }
	    Usuario usuario = new Usuario();
	    usuario.setUsername(dtoRegistro.username()); 
	    usuario.setPassword(passwordEncoder.encode(dtoRegistro.password()));

	    Rol rol = rolRepository.findByNombre(role).orElse(null);

	    if (rol == null) {
	        return new ResponseEntity<>(new RegistroResponseDto("Rol no válido", null), HttpStatus.BAD_REQUEST);
	    }

	    usuario.setRol(Collections.singletonList(rol)); 
	    usuarioRepository.save(usuario);

	    // Obtener el usuario recién creado con ID generado
	    Optional<Usuario> usuarioGuardado = usuarioRepository.findByUsername(dtoRegistro.username());

	    if (usuarioGuardado != null) {
	        RegistroResponseDto responseDto = new RegistroResponseDto("Registro exitoso", usuarioGuardado.get().getIdUsuario());
	        return new ResponseEntity<>(responseDto, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(new RegistroResponseDto("Error al obtener el ID del usuario", null), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
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
		usuarioEntity.setUsername(usuario.username());
		usuarioEntity.setPassword(usuario.password());
		return usuarioRepository.save(usuarioEntity);
	}


}
