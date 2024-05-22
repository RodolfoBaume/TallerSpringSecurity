package com.tallerMecanico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tallerMecanico.entity.OrdenServicio;

@Repository
public interface IOrdenServicioRepository extends JpaRepository<OrdenServicio, Long>{

	@Query("SELECT o FROM OrdenServicio o WHERE o.estatusServicio.estatusServicio = :estatus")
    List<OrdenServicio> findByEstatusServicio(@Param("estatus") String estatus);
	
	@Query("SELECT os FROM OrdenServicio os JOIN FETCH os.vehiculo WHERE os.idOrdenServicio = :id")
    OrdenServicio findByIdWithVehiculo(Long id);
	
	@Query("SELECT os FROM OrdenServicio os LEFT JOIN FETCH os.vehiculo")
    List<OrdenServicio> findAllWithVehiculo();
	
	@Query("SELECT o FROM OrdenServicio o WHERE o.estatusServicio.departamento.idDepartamento = :idDepartamento")
    List<OrdenServicio> findByDepartamentoId(@Param("idDepartamento") Long idDepartamento);
}
