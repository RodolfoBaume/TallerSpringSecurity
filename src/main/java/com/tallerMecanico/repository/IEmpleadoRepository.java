package com.tallerMecanico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tallerMecanico.entity.Empleado;

@Repository
public interface IEmpleadoRepository extends JpaRepository<Empleado, Long>{

}
