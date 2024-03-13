package com.tallerMecanico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tallerMecanico.entity.Marca;

@Repository
public interface IMarcaRepository extends JpaRepository<Marca, Long>{

}
