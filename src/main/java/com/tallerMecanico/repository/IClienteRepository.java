package com.tallerMecanico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tallerMecanico.entity.Cliente;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long> {

	@Query("SELECT c FROM Cliente c WHERE c.nombre LIKE %?1% OR c.apellidoPaterno LIKE %?1% OR c.apellidoMaterno LIKE %?1% OR c.telefono LIKE %?1%")
	List<Cliente> findByNombreApellidoPaternoApellidoMaternoTelefonoLike(String searchTerm);

}
