package com.tallerMecanico.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tallerMecanico.entity.Cliente;
import com.tallerMecanico.projection.IClienteClosedView;
import com.tallerMecanico.projection.IClienteProjection;
import com.tallerMecanico.projection.IVehiculoClosedView;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long>{
	
	@Query("SELECT c FROM Cliente c WHERE CONCAT(lower(c.nombre),' ',lower(c.apellidoPaterno)) " 
		+ " LIKE %?1% OR CONCAT(lower(c.apellidoPaterno),' ',lower(c.apellidoMaterno)) LIKE %?1% "
		+ " OR c.apellidoMaterno LIKE %?1% OR c.telefono LIKE %?1%")
	List<Cliente> findByNombreApellidoPaternoApellidoMaternoTelefonoLike(String searchTerm);

	List<IClienteClosedView>findBy();
	
	
	@Query("SELECT c FROM Cliente c")
    List<IClienteClosedView> findAllClientes();
		
	
	@Query("SELECT c FROM Cliente c")
    Page<IClienteClosedView> findAllClientes(Pageable pageable);
	
	@Query("SELECT v FROM Vehiculo v WHERE v.cliente.idCliente = :idCliente")
    List<IVehiculoClosedView> findVehiculosByClienteId(@Param("idCliente") Long idCliente);
	
	
	@Query("SELECT c.idCliente AS idCliente, c.nombre AS nombre, c.apellidoPaterno AS apellidoPaterno, " +
	           "c.apellidoMaterno AS apellidoMaterno, c.domicilio AS domicilio, c.telefono AS telefono " +
	           "FROM Cliente c")
	List<IClienteProjection> findAllProjectedBy(Sort sort);
	
}
