package com.tallerMecanico.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tallerMecanico.dto.MarcaDto;
import com.tallerMecanico.entity.Marca;
import com.tallerMecanico.repository.IMarcaRepository;

@Service
public class MarcaService implements IMarcaService {

	@Autowired
	private IMarcaRepository marcaRepository;

	// Consulta todos
	@Transactional(readOnly = true)
	public List<Marca> findAll() {
		return (List<Marca>) marcaRepository.findAll(Sort.by("idMarca"));
	}

	// consulta todos para paginación
	@Transactional(readOnly = true)
	public Page<Marca> findAllPage(Pageable pageable) {
		return marcaRepository.findAll(pageable);
	}

	// consulta por id
	@Transactional(readOnly = true)
	public Marca findById(Long idMarca) {
		return marcaRepository.findById(idMarca).orElse(null);
	}

	// Crear
	@Transactional
	public Marca createMarca(MarcaDto marca) {
		Marca marcaEntity = new Marca();
		marcaEntity.setMarca(marca.marca());
		return marcaRepository.save(marcaEntity);
	}

	// Eliminar
	public Marca deleteMarca(Long idMarca) {
		marcaRepository.deleteById(idMarca);
		return null;
	}

	// Modificar
	@Transactional
	public Marca updateMarca(Long idMarca, MarcaDto marca) {
		Marca marcaEntity = marcaRepository.findById(idMarca)
				.orElseThrow(() -> new NoSuchElementException("Marca no encontrada con el ID: " + idMarca));
		marcaEntity.setMarca(marca.marca());
		return marcaRepository.save(marcaEntity);
	}
}
