package com.tallerMecanico.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tallerMecanico.entity.Usuario;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {
	// Método para poder buscar un usuario mediante su nombre
	Optional<Usuario> findByUsername(String username);

	// Método para poder verificar si un usuario existe en nuestra base de datos
	Boolean existsByUsername(String username);

	@Query(value = "SELECT * From usuarios where username = ?1", nativeQuery = true)
	Usuario findCustomerByUsername(String username);

	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE usuarios SET password=?1 where username = ?2", nativeQuery = true)
	void setPassword(String password, String username);

}
