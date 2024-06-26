package com.tallerMecanico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tallerMecanico.entity.Modelo;
import com.tallerMecanico.projection.IModeloClosedView;

@Repository
public interface IModeloRepository extends JpaRepository<Modelo, Long>{
	
	List<Modelo> findByMarca_IdMarca(Long marcaId);

	List<IModeloClosedView> findBy();
	
}
