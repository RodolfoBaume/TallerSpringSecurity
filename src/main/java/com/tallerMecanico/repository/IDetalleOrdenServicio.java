package com.tallerMecanico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tallerMecanico.entity.DetalleOrdenServicio;

public interface IDetalleOrdenServicio extends JpaRepository<DetalleOrdenServicio, Long> {

}
