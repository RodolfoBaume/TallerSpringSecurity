package com.tallerMecanico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tallerMecanico.entity.Factura;

@Repository
public interface IFacturaRepository extends JpaRepository<Factura, Long>{

}
