package com.tallerMecanico.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tallerMecanico.entity.Factura;
import com.tallerMecanico.projection.IFacturaProjection;

@Repository
public interface IFacturaRepository extends JpaRepository<Factura, Long> {

	List<IFacturaProjection> findBy();

	@Query("SELECT f FROM Factura f")
	Page<IFacturaProjection> findAllFacturas(Pageable pageable);

	
	@Query("SELECT f FROM Factura f") IFacturaProjection
	findFacturaById(@Param("idFactura") Long idFactura);

}
