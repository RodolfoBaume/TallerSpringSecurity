package com.tallerMecanico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tallerMecanico.entity.Departamento;

@Repository
public interface IDepartamentoRepository extends JpaRepository<Departamento, Long> {

}
