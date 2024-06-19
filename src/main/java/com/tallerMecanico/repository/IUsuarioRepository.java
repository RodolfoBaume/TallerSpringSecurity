package com.tallerMecanico.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tallerMecanico.entity.Usuario;

public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {
	// Método para poder buscar un usuario mediante su nombre
	Optional<Usuario> findByUsername(String username);

	// Método para poder verificar si un usuario existe en nuestra base de datos
	Boolean existsByUsername(String username);

	@Query(value = "SELECT * From usuarios where username = ?1", nativeQuery = true)
	Usuario findCustomerByUsername( String username);
}
