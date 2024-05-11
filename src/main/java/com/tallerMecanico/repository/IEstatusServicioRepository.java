package com.tallerMecanico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tallerMecanico.entity.EstatusServicio;

@Repository
public interface IEstatusServicioRepository extends JpaRepository<EstatusServicio, Long> {
	List<EstatusServicio> findByDepartamento_IdDepartamento(Long departamentoId);
}
