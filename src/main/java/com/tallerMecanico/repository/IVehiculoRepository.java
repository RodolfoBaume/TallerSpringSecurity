package com.tallerMecanico.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tallerMecanico.entity.Vehiculo;
import com.tallerMecanico.projection.IVehiculoClienteClosedView;
import com.tallerMecanico.projection.IVehiculoClosedView;
import com.tallerMecanico.projection.IVehiculoConOrdenClosedView;
import com.tallerMecanico.projection.IVehiculoSinOrden;

@Repository
public interface IVehiculoRepository extends JpaRepository<Vehiculo, Long>{
	
	List<IVehiculoClosedView>findByCliente_IdCliente(Long clienteId);

	List<IVehiculoConOrdenClosedView>findBy();
	
	@Query("SELECT v FROM Vehiculo v")
    Page<IVehiculoConOrdenClosedView> findBy(Pageable pageable);
	
		
	//@Query("SELECT v FROM Vehiculo v WHERE v.id = ?1")
	@Query("SELECT v FROM Vehiculo v WHERE v.id = :idVehiculo")
	Optional<IVehiculoConOrdenClosedView> findByIdVehiculo(@Param("idVehiculo") long id);
	
	//query para clientes
	@Query("SELECT v FROM Vehiculo v WHERE v.id = :idVehiculo AND v.cliente.id = :idCliente")
	Optional<IVehiculoConOrdenClosedView> findByIdVehiculoAndClienteId(@Param("idVehiculo") long idVehiculo, @Param("idCliente") long idCliente);
	
	@Query("SELECT v FROM Vehiculo v LEFT JOIN FETCH v.tipoMotor tm LEFT JOIN FETCH v.modelo m LEFT JOIN FETCH v.cliente c LEFT JOIN FETCH OrdenServicio os ON v.idVehiculo = os.vehiculo.idVehiculo")
    List<IVehiculoClienteClosedView> findAllVehiculosWithDetails(Pageable pageable);

	
	@Query("SELECT v FROM Vehiculo v JOIN v.ordenServicio os JOIN os.estatusServicio es WHERE es.estatusServicio <> :estatus")
    List<IVehiculoConOrdenClosedView> findVehiculosByOrdenServicioEstatus(@Param("estatus") String estatus);

	@Query("SELECT v FROM Vehiculo v JOIN v.ordenServicio os JOIN os.estatusServicio es WHERE es.estatusServicio <> :estatus")
    Page<IVehiculoConOrdenClosedView> findVehiculosByOrdenServicioEstatus(@Param("estatus") String estatus, Pageable pageable);

	//Reporte
	@Query("SELECT v.idVehiculo AS idVehiculo, v.vin AS vin, v.matricula AS matricula, " +
	           "v.anioModelo AS anioModelo, v.color AS color, v.imagen AS imagen, " +
	           "v.tipoMotor AS tipoMotor, v.modelo AS modelo, v.cliente AS cliente " +
	           "FROM Vehiculo v")
	List<IVehiculoSinOrden> findAllProjectedBy();
}
