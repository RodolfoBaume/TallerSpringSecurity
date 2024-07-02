package com.tallerMecanico.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tallerMecanico.entity.OrdenServicio;
import com.tallerMecanico.projection.IOrdenServicioDepto;
import com.tallerMecanico.projection.IOrdenServicioProjection;
import com.tallerMecanico.projection.IOrdenServicioSinDetalle;

@Repository
public interface IOrdenServicioRepository extends JpaRepository<OrdenServicio, Long>{

	
	@Query("SELECT o FROM OrdenServicio o " +
		       "JOIN FETCH o.vehiculo v " +
		       "JOIN FETCH v.cliente c " +
		       "JOIN FETCH o.empleado e " + 
		       "WHERE o.idOrdenServicio = :idOrdenServicio")
	IOrdenServicioProjection findProjectedById(@Param("idOrdenServicio") Long idOrdenServicio);

    @Query("SELECT o FROM OrdenServicio o JOIN FETCH o.vehiculo v JOIN FETCH v.cliente c")
    List<IOrdenServicioSinDetalle> findAllProjected();
    
    @Query("SELECT o FROM OrdenServicio o JOIN FETCH o.vehiculo v JOIN FETCH v.cliente c")
    Slice<IOrdenServicioSinDetalle> findAllProjected(Pageable pageable);
    
    @Query("SELECT os.idOrdenServicio as idOrdenServicio, os.fechaOrden as fechaOrden, os.falla as falla, os.kilometraje as kilometraje, os.observaciones as observaciones, os.comentarios as comentarios, " +
		       "es as estatusServicio, d.departamento as departamento, " +
		       "v as vehiculo, " +
		       "e as empleado " + 
		       "FROM OrdenServicio os " +
		       "JOIN os.estatusServicio es " +
		       "JOIN es.departamento d " +
		       "JOIN os.vehiculo v " +
		       "JOIN v.modelo m " +
		       "JOIN m.marca ma " +
		       "JOIN os.empleado e " + 
    		   "WHERE es.estatusServicio = :estatus")
    List<IOrdenServicioDepto> findByEstatusServicio(@Param("estatus") String estatus);

    
    @Query("SELECT os.idOrdenServicio as idOrdenServicio, os.fechaOrden as fechaOrden, os.falla as falla, os.kilometraje as kilometraje, os.observaciones as observaciones, os.comentarios as comentarios, " +
		       "es as estatusServicio, d.departamento as departamento, " +
		       "v as vehiculo, " +
		       "e as empleado " + 
		       "FROM OrdenServicio os " +
		       "JOIN os.estatusServicio es " +
		       "JOIN es.departamento d " +
		       "JOIN os.vehiculo v " +
		       "JOIN v.modelo m " +
		       "JOIN m.marca ma " +
		       "JOIN os.empleado e " + 
 		   "WHERE es.estatusServicio = :estatus")
    Page<IOrdenServicioDepto> findByEstatusServicio(@Param("estatus") String estatus, Pageable pageable);    
    
    
	@Query("SELECT o FROM OrdenServicio o WHERE o.estatusServicio.departamento.idDepartamento = :idDepartamento")
    List<OrdenServicio> findByDepartamentoId1(@Param("idDepartamento") Long idDepartamento);
	
	@Query("SELECT os.idOrdenServicio as idOrdenServicio, os.fechaOrden as fechaOrden, os.falla as falla, os.kilometraje as kilometraje, os.observaciones as observaciones, os.comentarios as comentarios, " +
		       "es as estatusServicio, d.departamento as departamento, " +
		       "v as vehiculo, " +
		       "e as empleado " + 
		       "FROM OrdenServicio os " +
		       "JOIN os.estatusServicio es " +
		       "JOIN es.departamento d " +
		       "JOIN os.vehiculo v " +
		       "JOIN v.modelo m " +
		       "JOIN m.marca ma " +
		       "JOIN os.empleado e " + 
		       "WHERE d.idDepartamento = :idDepartamento")
	List<IOrdenServicioDepto> findByDepartamentoId(@Param("idDepartamento") Long idDepartamento);
	
	@Query("SELECT os.idOrdenServicio as idOrdenServicio, os.fechaOrden as fechaOrden, os.falla as falla, os.kilometraje as kilometraje, os.observaciones as observaciones, os.comentarios as comentarios, " +
		       "es as estatusServicio, d.departamento as departamento, " +
		       "v as vehiculo, " +
		       "e as empleado " + 
		       "FROM OrdenServicio os " +
		       "JOIN os.estatusServicio es " +
		       "JOIN es.departamento d " +
		       "JOIN os.vehiculo v " +
		       "JOIN v.modelo m " +
		       "JOIN m.marca ma " +
		       "JOIN os.empleado e " + 
		       "WHERE d.idDepartamento = :idDepartamento")
	Page<IOrdenServicioDepto> findByDepartamentoId(@Param("idDepartamento") Long idDepartamento, Pageable pageable);
	
}
