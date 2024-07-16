package com.tallerMecanico.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tallerMecanico.entity.Factura;
import com.tallerMecanico.projection.IFacturaProjection;
import com.tallerMecanico.projection.IFacturaReporte;

@Repository
public interface IFacturaRepository extends JpaRepository<Factura, Long> {

	List<IFacturaProjection> findBy();

	@Query("SELECT f FROM Factura f")
	Page<IFacturaProjection> findAllFacturas(Pageable pageable);

	
	@Query("SELECT f FROM Factura f") IFacturaProjection
	findFacturaById(@Param("idFactura") Long idFactura);

	@Query("SELECT f.idFactura AS idFactura, f.fechaFactura AS fechaFactura, f.monto AS monto, " +
	           "c.nombre AS clienteNombre, c.apellidoPaterno AS clienteApellidoPaterno, c.apellidoMaterno AS clienteApellidoMaterno, " +
	           "v.vin AS vehiculoVin, v.matricula AS vehiculoMatricula, m.modelo AS vehiculoModelo " +
	           "FROM Factura f " +
	           "JOIN f.ordenServicio o " +
	           "JOIN o.vehiculo v " +
	           "JOIN v.cliente c " +
	           "JOIN v.modelo m " +
	           "WHERE f.fechaFactura BETWEEN :startDate AND :endDate")
	List<IFacturaReporte> findFacturasInRange(Date startDate, Date endDate);
}
