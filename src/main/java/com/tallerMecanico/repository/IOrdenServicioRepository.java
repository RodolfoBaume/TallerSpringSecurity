package com.tallerMecanico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tallerMecanico.entity.OrdenServicio;

@Repository
public interface IOrdenServicioRepository extends JpaRepository<OrdenServicio, Long>{

}
