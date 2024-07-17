package com.tallerMecanico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tallerMecanico.entity.DetalleOrdenServicio;
import com.tallerMecanico.entity.OrdenServicio;
import com.tallerMecanico.projection.IDetalleOrdenServicioProjection;

@Repository
public interface IDetalleOrdenServicioRepository extends JpaRepository<DetalleOrdenServicio, Long>{
	
	List<IDetalleOrdenServicioProjection> findByOrdenServicio(OrdenServicio ordenServicio);
	
	List<DetalleOrdenServicio> findByOrdenServicio_IdOrdenServicio(long ordenServicioId);
}
