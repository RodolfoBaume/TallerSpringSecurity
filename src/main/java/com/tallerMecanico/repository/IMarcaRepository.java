package com.tallerMecanico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tallerMecanico.entity.Marca;
import com.tallerMecanico.projection.IMarcaClosedView;

@Repository
public interface IMarcaRepository extends JpaRepository<Marca, Long>{

	List<IMarcaClosedView>findBy();
}
