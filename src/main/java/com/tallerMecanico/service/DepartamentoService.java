package com.tallerMecanico.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tallerMecanico.dto.DepartamentoDto;
import com.tallerMecanico.entity.Departamento;
import com.tallerMecanico.repository.IDepartamentoRepository;

@Service
public class DepartamentoService {
	@Autowired
	private IDepartamentoRepository departamentoRepository;
	
	// Consulta todos
		@Transactional(readOnly = true)
		public List<Departamento> findAll() {
			return (List<Departamento>) departamentoRepository.findAll(Sort.by("idDepartamento"));
		}

		// consulta todos para paginación
		@Transactional(readOnly = true)
		public Page<Departamento> findAllPage(Pageable pageable) {
			return departamentoRepository.findAll(pageable);
		}

		// consulta por id
		@Transactional(readOnly = true)
		public Departamento findById(Long idDepartamento) {
			return departamentoRepository.findById(idDepartamento).orElse(null);
		}

		// Crear
		@Transactional
		public Departamento createDepartamento(DepartamentoDto departamento) {
			Departamento departamentoEntity = new Departamento();
			departamentoEntity.setDepartamento(departamento.departamento());
			return departamentoRepository.save(departamentoEntity);
		}

		// Eliminar
		public Departamento deleteDepartamento(Long idDepartamento) {
			departamentoRepository.deleteById(idDepartamento);
			return null;
		}

		// Modificar
		@Transactional
		public Departamento updateDepartamento(Long idDepartamento, DepartamentoDto departamento) {
			Departamento departamentoEntity = departamentoRepository.findById(idDepartamento)
					.orElseThrow(() -> new NoSuchElementException("Departamento no encontrado con el ID: " + idDepartamento));
			departamentoEntity.setDepartamento(departamento.departamento());
			return departamentoRepository.save(departamentoEntity);
		}
}
