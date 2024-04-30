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

import com.tallerMecanico.dto.DetalleOrdenServicioDto;
import com.tallerMecanico.entity.DetalleOrdenServicio;
import com.tallerMecanico.service.DetalleOrdenServicioService;

@RestController
@CrossOrigin(origins = "http://localhost:4200", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping("/api")
public class DetalleOrdenServicioController {

	@Autowired
	private DetalleOrdenServicioService detalleOrdenServicioService;

	// Consulta todos
	@GetMapping("/detalleOrdenServicio")
	@ResponseStatus(HttpStatus.OK)
	public List<DetalleOrdenServicio> consulta() {
		return detalleOrdenServicioService.findAll();
	}

	// Consulta paginación
	@GetMapping("/detalleOrdenServicio/page/{page}")
	public Page<DetalleOrdenServicio> consultaPage(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 10, Sort.by("idDetalleOrdenServicio").ascending());
		return detalleOrdenServicioService.findAllPage(pageable);
	}

	// Consulta por id
	@GetMapping("/detalleOrdenServicio/{id}")
	public ResponseEntity<?> consultaPorID(@PathVariable Long id) {

		DetalleOrdenServicio detalleOrdenServicio = null;
		String response = "";
		try {
			detalleOrdenServicio = detalleOrdenServicioService.findById(id);
		} catch (DataAccessException e) {
			response = "Error al realizar la consulta.";
			response = response.concat(e.getMessage().concat(e.getMostSpecificCause().toString()));
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (detalleOrdenServicio == null) {
			response = "El Detalle de la Orden de Servicio con el ID: ".concat(id.toString())
					.concat(" no existe en la base de datos");
			return new ResponseEntity<String>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<DetalleOrdenServicio>(detalleOrdenServicio, HttpStatus.OK);
	}

	// Eliminar por id
	@DeleteMapping("/detalleOrdenServicio/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();

		try {
			DetalleOrdenServicio detalleOrdenServicioDelete = this.detalleOrdenServicioService.findById(id);
			if (detalleOrdenServicioDelete == null) {
				response.put("mensaje", "Error al eliminar. El Detalle de la Orden de Servicio no existe en base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			detalleOrdenServicioService.deleteDetalleOrdenServicio(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar en base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Detalle de la Orden de Servicio eliminada con éxito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	// Crear
	@PostMapping("/detalleOrdenServicio")
	public ResponseEntity<?> create(@RequestBody DetalleOrdenServicioDto detalleOrdenServicio) {
		DetalleOrdenServicio detalleOrdenServicioNew = null;
		Map<String, Object> response = new HashMap<>();

		try {
			detalleOrdenServicioNew = this.detalleOrdenServicioService.createDetalleOrdenServicio(detalleOrdenServicio);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje",
				"Orden de Servicio creada con éxito, con el ID " + detalleOrdenServicioNew.getIdDetalleOrdenServicio());
		response.put("Detalle de la Orden de Servicio", detalleOrdenServicioNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	// Modificar
	@PutMapping("/detalleOrdenServicio/{id}")
	public ResponseEntity<?> modify(@PathVariable Long id, @RequestBody DetalleOrdenServicioDto detalleOrdenServicio) {
		DetalleOrdenServicio detalleOrdenServicioNew = null;
		Map<String, Object> response = new HashMap<>();

		try {
			detalleOrdenServicioNew = this.detalleOrdenServicioService.updateDetalleOrdenServicio(id, detalleOrdenServicio);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el update en base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje",
				"Detalle de Orden de Servicio modificada con éxito, con el ID " + detalleOrdenServicioNew.getIdDetalleOrdenServicio());
		response.put("Detalle de Orden de Servicio", detalleOrdenServicioNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

}
