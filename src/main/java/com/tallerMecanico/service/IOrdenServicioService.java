package com.tallerMecanico.service;

import java.util.List;

import com.tallerMecanico.dto.OrdenServicioDto;
import com.tallerMecanico.entity.OrdenServicio;

public interface IOrdenServicioService {

	List<OrdenServicio> findAll();
	
	OrdenServicio findById(Long idOrdenServicio);
	
	OrdenServicio createOrdenServicio(OrdenServicioDto ordenServicio);
	
	OrdenServicio deleteOrdenServicio(Long idOrdenServicio);
	
	OrdenServicio updateOrdenServicio(Long idOrdenServicio, OrdenServicioDto ordenServicio);
	
}
