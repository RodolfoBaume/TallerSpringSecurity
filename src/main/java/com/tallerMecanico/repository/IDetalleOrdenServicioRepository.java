package com.tallerMecanico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tallerMecanico.entity.DetalleOrdenServicio;

@Repository
public interface IDetalleOrdenServicioRepository extends JpaRepository<DetalleOrdenServicio, Long>{

}
