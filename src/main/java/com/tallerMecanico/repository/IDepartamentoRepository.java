package com.tallerMecanico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tallerMecanico.entity.Departamento;
import com.tallerMecanico.projection.IDepartamentoClosedView;

public interface IDepartamentoRepository extends JpaRepository<Departamento, Long>{

	List<IDepartamentoClosedView>findBy();
}
