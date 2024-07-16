package com.tallerMecanico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tallerMecanico.dto.DetalleFacturasDto;
import com.tallerMecanico.dto.FacturaDetalleDto;
import com.tallerMecanico.entity.DetalleFactura;

@Repository
public interface IDetalleFacturaRepository extends JpaRepository<DetalleFactura, Long> {

	@Query("SELECT new com.tallerMecanico.dto.FacturaDetalleDto(f.idFactura, f.fechaFactura, f.monto, "
			+ "new com.tallerMecanico.dto.DetalleFacturasDto(df.idDetalleFactura, df.descripcionServicio, df.costo)) "
			+ "FROM DetalleFactura df JOIN df.factura f")
	List<FacturaDetalleDto> findAllWithDetalles();

	

	@Query("SELECT new com.tallerMecanico.dto.DetalleFacturasDto(df.idDetalleFactura, df.descripcionServicio, df.costo, df.factura.idFactura) "
			+ "FROM DetalleFactura df")
	List<DetalleFacturasDto> findAllDetalles();

	@Query("SELECT new com.tallerMecanico.dto.FacturaDetalleDto(f.idFactura, f.fechaFactura, f.monto, null) " + "FROM Factura f")
	List<FacturaDetalleDto> findAllFacturas();
}
