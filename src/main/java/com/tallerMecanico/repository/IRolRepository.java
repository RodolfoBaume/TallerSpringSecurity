package com.tallerMecanico.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tallerMecanico.entity.Rol;

public interface IRolRepository extends JpaRepository<Rol, Long> {
	// Método para buscar un role por su nombre en nuestra base de datos
	Optional<Rol> findByNombre(String nombre);
}
