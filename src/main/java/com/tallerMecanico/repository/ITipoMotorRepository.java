package com.tallerMecanico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tallerMecanico.entity.TipoMotor;
import com.tallerMecanico.projection.ITipoMotorClosedView;

@Repository
public interface ITipoMotorRepository extends JpaRepository<TipoMotor, Long>{
	
	List<ITipoMotorClosedView>findBy();

}
