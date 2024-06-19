package com.tallerMecanico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tallerMecanico.entity.Empleado;

public interface IEmpleadoRepository extends JpaRepository<Empleado, Long>{

}
