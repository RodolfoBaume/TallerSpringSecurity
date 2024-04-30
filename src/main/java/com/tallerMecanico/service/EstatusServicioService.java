package com.tallerMecanico.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tallerMecanico.dto.EstatusServicioDto;
import com.tallerMecanico.entity.EstatusServicio;
import com.tallerMecanico.repository.IEstatusServicioRepository;

@Service
public class EstatusServicioService implements IEstatusServicioService {

	@Autowired
	private IEstatusServicioRepository estatusServicioRepository;

	// Consulta todos
	@Transactional(readOnly = true)
	public List<EstatusServicio> findAll() {
		return (List<EstatusServicio>) estatusServicioRepository.findAll(Sort.by("idEstatusServicio"));
	}

	// consulta todos para paginación
	@Transactional(readOnly = true)
	public Page<EstatusServicio> findAllPage(Pageable pageable) {
		return estatusServicioRepository.findAll(pageable);
	}
	
	// consulta por id
	@Transactional(readOnly = true)
	public EstatusServicio findById(Long idEstatusServicio) {
		return estatusServicioRepository.findById(idEstatusServicio).orElse(null);
	}

	// Crear
	@Transactional
	public EstatusServicio createEstatusServicio(EstatusServicioDto estatusServicio) {
		EstatusServicio estatusServicioEntity = new EstatusServicio();
		estatusServicioEntity.setEstatusServicio(estatusServicio.estatusServicio());
		return estatusServicioRepository.save(estatusServicioEntity);
	}

	// Eliminar
	public EstatusServicio deleteEstatusServicio(Long idEstatusServicio) {
		estatusServicioRepository.deleteById(idEstatusServicio);
		return null;
	}

	// Modificar
	@Transactional
	public EstatusServicio updateEstatusServicio(Long idEstatusServicio, EstatusServicioDto estatusServicio) {
		EstatusServicio estatusServicioEntity = estatusServicioRepository.findById(idEstatusServicio)
				.orElseThrow(() -> new NoSuchElementException("Estatus de servicio no encontrado con el ID: " + idEstatusServicio));
		estatusServicioEntity.setEstatusServicio(estatusServicio.estatusServicio());
		return estatusServicioRepository.save(estatusServicioEntity);
	}
}
