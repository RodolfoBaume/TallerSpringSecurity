package com.tallerMecanico.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tallerMecanico.dto.DetalleOrdenServicioDto;
import com.tallerMecanico.entity.DetalleOrdenServicio;

public interface IDetalleOrdenServicioService {

	List<DetalleOrdenServicio> findAll();
	
	Page<DetalleOrdenServicio> findAllPage(Pageable pageable);
	
	DetalleOrdenServicio findById(Long idDetalleOrdenServicio);
	
	DetalleOrdenServicio createDetalleOrdenServicio(DetalleOrdenServicioDto detalleOrdenServicio);
	
	DetalleOrdenServicio deleteDetalleOrdenServicio(Long idDetalleOrdenServicio);
	
	DetalleOrdenServicio updateDetalleOrdenServicio(Long idDetalleOrdenServicio, DetalleOrdenServicioDto detalleOrdenServicio);
}
