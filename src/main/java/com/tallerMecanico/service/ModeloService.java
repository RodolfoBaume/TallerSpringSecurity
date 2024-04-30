package com.tallerMecanico.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tallerMecanico.dto.ModeloDto;
import com.tallerMecanico.entity.Modelo;
import com.tallerMecanico.repository.IModeloRepository;

@Service
public class ModeloService implements IModeloService {

	@Autowired
	private IModeloRepository modeloRepository;

	// Consulta todos
	@Transactional(readOnly = true)
	public List<Modelo> findAll() {
		return (List<Modelo>) modeloRepository.findAll(Sort.by("idModelo"));
	}

	// consulta todos para paginación
	@Transactional(readOnly = true)
	public Page<Modelo> findAllPage(Pageable pageable) {
		return modeloRepository.findAll(pageable);
	}

	// consulta por id
	@Transactional(readOnly = true)
	public Modelo findById(Long idModelo) {
		return modeloRepository.findById(idModelo).orElse(null);
	}

	// Crear
	@Transactional
	public Modelo createModelo(ModeloDto modelo) {
		Modelo modeloEntity = new Modelo();
		modeloEntity.setModelo(modelo.modelo());
		modeloEntity.setMarca(modelo.marca());
		return modeloRepository.save(modeloEntity);
	}

	// Eliminar
	public Modelo deleteModelo(Long idModelo) {
		modeloRepository.deleteById(idModelo);
		return null;
	}

	// Modificar
	@Transactional
	public Modelo updateModelo(Long idModelo, ModeloDto modelo) {
		Modelo modeloEntity = modeloRepository.findById(idModelo)
				.orElseThrow(() -> new NoSuchElementException("Modelo no encontrado con el ID: " + idModelo));
		modeloEntity.setModelo(modelo.modelo());
		modeloEntity.setMarca(modelo.marca());
		return modeloRepository.save(modeloEntity);
	}
}
