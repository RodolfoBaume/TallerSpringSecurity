package com.tallerMecanico.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tallerMecanico.dto.ReporteMesesDto;
import com.tallerMecanico.entity.Factura;
import com.tallerMecanico.projection.IDetalleFacturaProjection;
import com.tallerMecanico.projection.IFacturaClosedView;
import com.tallerMecanico.projection.IFacturaProjection;
import com.tallerMecanico.projection.IFacturaReporte;

@Repository
public interface IFacturaRepository extends JpaRepository<Factura, Long> {
	

	List<IFacturaProjection> findBy();
	/*
	@Query("SELECT f FROM Factura f")
	Page<IFacturaProjection> findAllFacturas(Pageable pageable);
*/
	
	/*
	@Query("SELECT f FROM Factura f")
	IFacturaProjection findFacturaById(@Param("idFactura") Long idFactura);
	*/
	
	@Query(value = "SELECT  sum(monto) as total, date_trunc('month', fecha_factura) as mes\n" + 
				" FROM facturas  WHERE fecha_factura  BETWEEN '2023/08/01' AND '2024/07/31'\n" + 
				" Group by date_trunc('month', fecha_factura);", nativeQuery = true)
	List<?> obtenerReportePorMeses();

	@Query("SELECT f.idFactura as idFactura, f.fechaFactura as fechaFactura, f.monto as monto " +
		       "FROM Factura f WHERE f.idFactura = :idFactura")
	IFacturaClosedView findFacturaById(@Param("idFactura") Long idFactura);

	// consulta facturas detalle
	@Query("SELECT f FROM Factura f")
	List<IFacturaClosedView> findAllFacturas();

	// consulta facturas detalle
	@Query("SELECT f FROM Factura f")
	Page<IFacturaClosedView> findAllFacturas(Pageable pageable);

	@Query("SELECT df FROM DetalleFactura df WHERE df.factura.idFactura = :idFactura")
	List<IDetalleFacturaProjection> findDetalleFacturaByFacturaId(@Param("idFactura") Long idFactura);

	/*
	@Query("SELECT f.idFactura as idFactura, f.fechaFactura as fechaFactura, f.monto as monto, "
			+ "df.idDetalleFactura as idDetalleFactura, df.descripcionServicio as descripcionServicio, df.costo as costo "
			+ "FROM Factura f LEFT JOIN DetalleFactura df ON f.idFactura = df.factura.idFactura")
	List<IFacturaProjection> findAllWithDetalles();
*/
	@Query("SELECT f.idFactura AS idFactura, f.fechaFactura AS fechaFactura, f.monto AS monto, "
			+ "c.nombre AS clienteNombre, c.apellidoPaterno AS clienteApellidoPaterno, c.apellidoMaterno AS clienteApellidoMaterno, "
			+ "v.vin AS vehiculoVin, v.matricula AS vehiculoMatricula, m.modelo AS vehiculoModelo " + "FROM Factura f "
			+ "JOIN f.ordenServicio o " + "JOIN o.vehiculo v " + "JOIN v.cliente c " + "JOIN v.modelo m "
			+ "WHERE f.fechaFactura BETWEEN :startDate AND :endDate")
	List<IFacturaReporte> findFacturasInRange(Date startDate, Date endDate);
}
