package com.tallerMecanico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tallerMecanico.entity.Vehiculo;

@Repository
public interface IVehiculoRepository extends JpaRepository<Vehiculo, Long> {
	
	List<Vehiculo> findByCliente_IdCliente(Long clienteId);

	@Query("SELECT DISTINCT v FROM Vehiculo v LEFT JOIN FETCH v.ordenServicio")
	List<Vehiculo> findAllWithOrdenServicio();
}
