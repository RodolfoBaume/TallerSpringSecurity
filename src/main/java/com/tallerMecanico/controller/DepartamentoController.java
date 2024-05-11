package com.tallerMecanico.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tallerMecanico.dto.DepartamentoDto;
import com.tallerMecanico.entity.Departamento;
import com.tallerMecanico.entity.EstatusServicio;
import com.tallerMecanico.repository.IEstatusServicioRepository;
import com.tallerMecanico.service.DepartamentoService;

@RestController
@CrossOrigin(origins = "http://localhost:4200", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping("/api")
public class DepartamentoController {

	@Autowired
	private DepartamentoService departamentoService;

	@Autowired
	private IEstatusServicioRepository estatusServicioRepository;

	// Consulta todos
	@GetMapping("/departamentos")
	@ResponseStatus(HttpStatus.OK)
	public List<Departamento> consulta() {
		return departamentoService.findAll();
	}

	// Consulta paginación
	@GetMapping("/departamentos/page/{page}")
	public Page<Departamento> consultaPage(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 10, Sort.by("idDepartamento").ascending());
		return departamentoService.findAllPage(pageable);
	}

	// Consulta por id
	@GetMapping("/departamentos/{id}")
	public ResponseEntity<?> consultaPorID(@PathVariable Long id) {

		Departamento departamento = null;
		String response = "";
		try {
			departamento = departamentoService.findById(id);
		} catch (DataAccessException e) {
			response = "Error al realizar la consulta.";
			response = response.concat(e.getMessage().concat(e.getMostSpecificCause().toString()));
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (departamento == null) {
			response = "El departamento con el ID: ".concat(id.toString()).concat(" no existe en la base de datos");
			return new ResponseEntity<String>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Departamento>(departamento, HttpStatus.OK);
	}

	// Consultar los estatus por departamento
	@GetMapping("departamentos/{idDepartamento}/estatus")
	public List<EstatusServicio> getEstatusByDepartamento(@PathVariable Long idDepartamento) {
		return estatusServicioRepository.findByDepartamento_IdDepartamento(idDepartamento);
	}

	// Eliminar por id
	@DeleteMapping("/departamentos/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();

		try {
			Departamento departamentoDelete = this.departamentoService.findById(id);
			if (departamentoDelete == null) {
				response.put("mensaje", "Error al eliminar. El Departamento no existe en base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			departamentoService.deleteDepartamento(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar en base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Departamento eliminado con éxito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	// Crear
	@PostMapping("/departamentos")
	public ResponseEntity<?> create(@RequestBody DepartamentoDto departamento) {
		Departamento departamentoNew = null;
		Map<String, Object> response = new HashMap<>();

		try {
			departamentoNew = this.departamentoService.createDepartamento(departamento);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Departamento creado con éxito, con el ID " + departamentoNew.getIdDepartamento());
		response.put("departamento", departamentoNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	// Modificar
	@PutMapping("/departamentos/{id}")
	public ResponseEntity<?> modify(@PathVariable Long id, @RequestBody DepartamentoDto departamento) {
		Departamento departamentoNew = null;
		Map<String, Object> response = new HashMap<>();

		try {
			departamentoNew = this.departamentoService.updateDepartamento(id, departamento);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el update en base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Departamento modificado con éxito, con el ID " + departamentoNew.getIdDepartamento());
		response.put("departamento", departamentoNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
}
