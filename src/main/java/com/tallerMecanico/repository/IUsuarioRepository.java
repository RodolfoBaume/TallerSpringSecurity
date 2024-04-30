package com.tallerMecanico.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tallerMecanico.entity.Usuario;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long>{

	/*
	 * // Método para poder buscar un usuario mediante su nombre Optional <Usuario>
	 * findByEmail(String email);
	 * 
	 * //Método para poder verificar si un usuario existe en nuestra base de datos
	 * Boolean existsByEmail(String email);
	 */
		
		// Método para poder buscar un usuario mediante su nombre
		Optional<Usuario>findByUsername(String username);
		
		//Método para poder verificar si un usuario existe en nuestra base de datos
		Boolean existsByUsername(String username);
}
