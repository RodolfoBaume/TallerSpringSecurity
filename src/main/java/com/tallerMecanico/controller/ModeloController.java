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

import com.tallerMecanico.dto.ModeloDto;
import com.tallerMecanico.entity.Modelo;
import com.tallerMecanico.service.ModeloService;

@RestController
@CrossOrigin(origins = "http://localhost:4200", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping("/api")
public class ModeloController {

	@Autowired
	private ModeloService modeloService;

	// Consulta todos
	@GetMapping("/modelos")
	@ResponseStatus(HttpStatus.OK)
	public List<Modelo> consulta() {
		return modeloService.findAll();
	}

	// Consulta paginación
	@GetMapping("/modelos/page/{page}")
	public Page<Modelo> consultaPage(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 50, Sort.by("idModelo").ascending());
		return modeloService.findAllPage(pageable);
	}

	// Consulta por id
	@GetMapping("/modelos/{id}")
	public ResponseEntity<?> consultaPorID(@PathVariable Long id) {

		Modelo modelo = null;
		String response = "";
		try {
			modelo = modeloService.findById(id);
		} catch (DataAccessException e) {
			response = "Error al realizar la consulta.";
			response = response.concat(e.getMessage().concat(e.getMostSpecificCause().toString()));
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (modelo == null) {
			response = "El modelo con el ID: ".concat(id.toString()).concat(" no existe en la base de datos");
			return new ResponseEntity<String>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Modelo>(modelo, HttpStatus.OK);
	}

	// Eliminar por id
	@DeleteMapping("/modelos/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();

		try {
			Modelo modeloDelete = this.modeloService.findById(id);
			if (modeloDelete == null) {
				response.put("mensaje", "Error al eliminar. La marca no existe en base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			modeloService.deleteModelo(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar en base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Modelo eliminado con éxito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	// Crear
	@PostMapping("/modelos")
	public ResponseEntity<?> create(@RequestBody ModeloDto modelo) {
		Modelo modeloNew = null;
		Map<String, Object> response = new HashMap<>();

		try {
			modeloNew = this.modeloService.createModelo(modelo);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Modelo creada con éxito, con el ID " + modeloNew.getIdModelo());
		response.put("Modelo", modeloNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	// Modificar
	@PutMapping("/modelos/{id}")
	public ResponseEntity<?> modify(@PathVariable Long id, @RequestBody ModeloDto modelo) {
		Modelo modeloNew = null;
		Map<String, Object> response = new HashMap<>();

		try {
			modeloNew = this.modeloService.updateModelo(id, modelo);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el update en base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Marca modificada con éxito, con el ID " + modeloNew.getIdModelo());
		response.put("modelo", modeloNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
}
