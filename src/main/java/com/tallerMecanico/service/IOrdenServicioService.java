package com.tallerMecanico.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tallerMecanico.dto.OrdenServicioDto;
import com.tallerMecanico.entity.OrdenServicio;
import com.tallerMecanico.projection.IOrdenServicioDepto;

public interface IOrdenServicioService {

	List<OrdenServicio> findAll();
	
	Page<OrdenServicio> findAllPage(Pageable pageable);
	
	OrdenServicio findById(Long idOrdenServicio);
	
	OrdenServicio createOrdenServicio(OrdenServicioDto ordenServicio);
	
	OrdenServicio deleteOrdenServicio(Long idOrdenServicio);
	
	OrdenServicio updateOrdenServicio(Long idOrdenServicio, OrdenServicioDto ordenServicio);
	
	List<IOrdenServicioDepto> obtenerPorEstatusServicio(String estatus);
	
}
