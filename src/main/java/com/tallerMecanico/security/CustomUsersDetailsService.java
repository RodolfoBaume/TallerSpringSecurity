package com.tallerMecanico.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tallerMecanico.entity.Rol;
import com.tallerMecanico.entity.Usuario;
import com.tallerMecanico.repository.IUsuarioRepository;

@Service
public class CustomUsersDetailsService implements UserDetailsService{
	private IUsuarioRepository usuariosRepository;
	
	@Autowired
	public CustomUsersDetailsService(IUsuarioRepository usuariosRepository) {
		super();
		this.usuariosRepository = usuariosRepository;
	}

	// Método para traernos una lista de autoridades por medio de una lista de roles
	public Collection<GrantedAuthority>mapToAuthorities(List<Rol>roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getNombre())).collect(Collectors.toList());
	}

	// Método para traernos un usuario con todos sus datos por medio de su username
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuarios = usuariosRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Usuario no encontrado"));
		return new User(usuarios.getUsername(), usuarios.getPassword(), mapToAuthorities(usuarios.getRol()));
	}

}
