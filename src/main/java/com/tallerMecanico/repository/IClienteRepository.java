package com.tallerMecanico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tallerMecanico.entity.Cliente;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long>{

}
