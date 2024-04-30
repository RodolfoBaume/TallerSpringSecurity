package com.tallerMecanico.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tallerMecanico.dto.TipoMotorDto;
import com.tallerMecanico.entity.TipoMotor;
import com.tallerMecanico.repository.ITipoMotorRepository;

@Service
public class TipoMotorService implements ITipoMotorService {

	@Autowired
	private ITipoMotorRepository tipoMotorRepository;

	// Consulta todos
	@Transactional(readOnly = true)
	public List<TipoMotor> findAll() {
		return (List<TipoMotor>) tipoMotorRepository.findAll(Sort.by("idTipoMotor"));
	}

	// consulta todos para paginación
	@Transactional(readOnly = true)
	public Page<TipoMotor> findAllPage(Pageable pageable) {
		return tipoMotorRepository.findAll(pageable);
	}
	
	// consulta por id
	@Transactional(readOnly = true)
	public TipoMotor findById(Long idTipoMotor) {
		return tipoMotorRepository.findById(idTipoMotor).orElse(null);
	}

	// Crear
	@Transactional
	public TipoMotor createTipoMotor(TipoMotorDto tipoMotor) {
		TipoMotor tipoMotorEntity = new TipoMotor();
		tipoMotorEntity.setTipoMotor(tipoMotor.tipoMotor());
		return tipoMotorRepository.save(tipoMotorEntity);
	}

	// Eliminar
	public TipoMotor deleteTipoMotor(Long idTipoMotor) {
		tipoMotorRepository.deleteById(idTipoMotor);
		return null;
	}

	// Modificar
	@Transactional
	public TipoMotor updateTipoMotor(Long idTipoMotor, TipoMotorDto tipoMotor) {
		TipoMotor tipoMotorEntity = tipoMotorRepository.findById(idTipoMotor)
				.orElseThrow(() -> new NoSuchElementException("Tipo de Motor no encontrado con el ID: " + idTipoMotor));
		tipoMotorEntity.setTipoMotor(tipoMotor.tipoMotor());
		return tipoMotorRepository.save(tipoMotorEntity);
	}
}
