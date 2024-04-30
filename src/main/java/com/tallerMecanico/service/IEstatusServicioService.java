package com.tallerMecanico.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tallerMecanico.dto.EstatusServicioDto;
import com.tallerMecanico.entity.EstatusServicio;

public interface IEstatusServicioService {

	List<EstatusServicio> findAll();
	
	Page<EstatusServicio> findAllPage(Pageable pageable);
	
	EstatusServicio findById(Long idEstatusServicio);
	
	EstatusServicio createEstatusServicio(EstatusServicioDto estatusServicio);
	
	EstatusServicio deleteEstatusServicio(Long idEstatusServicio);
	
	EstatusServicio updateEstatusServicio(Long idEstatusServicio, EstatusServicioDto estatusServicio);
}
