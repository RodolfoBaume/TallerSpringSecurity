package com.tallerMecanico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tallerMecanico.entity.OrdenServicio;
import com.tallerMecanico.projection.IOrdenServicioDepto;
import com.tallerMecanico.projection.IOrdenServicioProjection;

@Repository
public interface IOrdenServicioRepository extends JpaRepository<OrdenServicio, Long>{

	@Query("SELECT o FROM OrdenServicio o JOIN FETCH o.vehiculo v JOIN FETCH v.cliente c WHERE o.idOrdenServicio = :idOrdenServicio")
    IOrdenServicioProjection findProjectedById(@Param("idOrdenServicio") Long idOrdenServicio);

    @Query("SELECT o FROM OrdenServicio o JOIN FETCH o.vehiculo v JOIN FETCH v.cliente c")
    List<IOrdenServicioProjection> findAllProjected();
    
    
    
    @Query("SELECT o FROM OrdenServicio o WHERE o.estatusServicio.estatusServicio = :estatus")
    List<OrdenServicio> findByEstatusServicio(@Param("estatus") String estatus);
	
	@Query("SELECT os FROM OrdenServicio os JOIN FETCH os.vehiculo WHERE os.idOrdenServicio = :id")
    OrdenServicio findByIdWithVehiculo(Long id);
	
	@Query("SELECT os FROM OrdenServicio os LEFT JOIN FETCH os.vehiculo")
    List<OrdenServicio> findAllWithVehiculo();
	
	/*
	@Query("SELECT o FROM OrdenServicio o WHERE o.estatusServicio.departamento.idDepartamento = :idDepartamento")
    List<OrdenServicio> findByDepartamentoId(@Param("idDepartamento") Long idDepartamento);
	*/
	
	
	
	@Query("SELECT os.idOrdenServicio as idOrdenServicio, os.fechaOrden as fechaOrden, os.falla as falla, os.kilometraje as kilometraje, os.observaciones as observaciones, os.comentarios as comentarios, es.estatusServicio as estatusServicio, d.departamento as departamento " +
	           "FROM OrdenServicio os " +
	           "JOIN os.estatusServicio es " +
	           "JOIN es.departamento d " +
	           "JOIN os.vehiculo v " +
	           "JOIN os.empleado e " +
	           "WHERE d.idDepartamento = :idDepartamento")
	    List<IOrdenServicioDepto> findByDepartamentoId(@Param("idDepartamento") Long idDepartamento);
}
