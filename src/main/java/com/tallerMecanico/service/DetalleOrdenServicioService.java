package com.tallerMecanico.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tallerMecanico.dto.DetalleOrdenServicioDto;
import com.tallerMecanico.entity.DetalleOrdenServicio;
import com.tallerMecanico.repository.IDetalleOrdenServicioRepository;

@Service
public class DetalleOrdenServicioService implements IDetalleOrdenServicioService {

	@Autowired
	private IDetalleOrdenServicioRepository detalleOrdenServicioRepository;

	// Consulta todos
	@Transactional(readOnly = true)
	public List<DetalleOrdenServicio> findAll() {
		return (List<DetalleOrdenServicio>) detalleOrdenServicioRepository.findAll(Sort.by("idDetalleOrdenServicio"));
	}

	// consulta todos para paginación
	@Transactional(readOnly = true)
	public Page<DetalleOrdenServicio> findAllPage(Pageable pageable) {
		return detalleOrdenServicioRepository.findAll(pageable);
	}

	// consulta por id
	@Transactional(readOnly = true)
	public DetalleOrdenServicio findById(Long idDetalleOrdenServicio) {
		return detalleOrdenServicioRepository.findById(idDetalleOrdenServicio).orElse(null);
	}

	// Crear
	@Transactional
	public DetalleOrdenServicio createDetalleOrdenServicio(DetalleOrdenServicioDto detalleOrdenServicio) {
		DetalleOrdenServicio detalleOrdenServicioEntity = new DetalleOrdenServicio();
		detalleOrdenServicioEntity.setDescripcionServicio(detalleOrdenServicio.descripcionServicio());
		detalleOrdenServicioEntity.setOrdenServicio(detalleOrdenServicio.ordenServicio());
		return detalleOrdenServicioRepository.save(detalleOrdenServicioEntity);
	}

	// Eliminar
	public DetalleOrdenServicio deleteDetalleOrdenServicio(Long idDetalleOrdenServicio) {
		detalleOrdenServicioRepository.deleteById(idDetalleOrdenServicio);
		return null;
	}

	// Modificar
	@Transactional
	public DetalleOrdenServicio updateDetalleOrdenServicio(Long idDetalleOrdenServicio, DetalleOrdenServicioDto detalleOrdenServicio) {
		DetalleOrdenServicio detalleOrdenServicioEntity = detalleOrdenServicioRepository.findById(idDetalleOrdenServicio)
				.orElseThrow(() -> new NoSuchElementException("Modelo no encontrado con el ID: " + idDetalleOrdenServicio));
		detalleOrdenServicioEntity.setDescripcionServicio(detalleOrdenServicio.descripcionServicio());
		detalleOrdenServicioEntity.setOrdenServicio(detalleOrdenServicio.ordenServicio());
		return detalleOrdenServicioRepository.save(detalleOrdenServicioEntity);
	}
}
