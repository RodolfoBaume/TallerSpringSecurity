package com.tallerMecanico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tallerMecanico.entity.TipoMotor;

@Repository
public interface ITipoMotorRepository extends JpaRepository<TipoMotor, Long>{

}
