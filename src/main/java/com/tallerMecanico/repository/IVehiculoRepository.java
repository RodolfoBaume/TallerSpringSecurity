package com.tallerMecanico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tallerMecanico.entity.Vehiculo;

@Repository
public interface IVehiculoRepository extends JpaRepository<Vehiculo, Long> {
	
	List<Vehiculo> findByCliente_IdCliente(Long clienteId);

}
