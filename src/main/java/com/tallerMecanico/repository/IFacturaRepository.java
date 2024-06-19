package com.tallerMecanico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tallerMecanico.entity.Factura;

public interface IFacturaRepository extends JpaRepository<Factura, Long>{

}
