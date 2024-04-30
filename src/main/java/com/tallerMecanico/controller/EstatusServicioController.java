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

import com.tallerMecanico.dto.EstatusServicioDto;
import com.tallerMecanico.entity.EstatusServicio;
import com.tallerMecanico.service.EstatusServicioService;

@RestController
@CrossOrigin(origins = "http://localhost:4200", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping("/api")
public class EstatusServicioController {

	@Autowired
	private EstatusServicioService estatusServicioService;

	// Consulta todos
	@GetMapping("/estatusServicios")
	@ResponseStatus(HttpStatus.OK)
	public List<EstatusServicio> consulta() {
		return estatusServicioService.findAll();
	}

	// Consulta paginación
	@GetMapping("/estatusServicios/page/{page}")
	public Page<EstatusServicio> consultaPage(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 10, Sort.by("idEstatusServicio").ascending());
		return estatusServicioService.findAllPage(pageable);
	}

	// Consulta por id
	@GetMapping("/estatusServicios/{id}")
	public ResponseEntity<?> consultaPorID(@PathVariable Long id) {

		EstatusServicio estatusServicio = null;
		String response = "";
		try {
			estatusServicio = estatusServicioService.findById(id);
		} catch (DataAccessException e) {
			response = "Error al realizar la consulta.";
			response = response.concat(e.getMessage().concat(e.getMostSpecificCause().toString()));
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (estatusServicio == null) {
			response = "el Estatus Servicio con el ID: ".concat(id.toString()).concat(" no existe en la base de datos");
			return new ResponseEntity<String>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<EstatusServicio>(estatusServicio, HttpStatus.OK);
	}

	// Eliminar por id
	@DeleteMapping("/estatusServicios/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();

		try {
			EstatusServicio estatusServicioDelete = this.estatusServicioService.findById(id);
			if (estatusServicioDelete == null) {
				response.put("mensaje", "Error al eliminar. La marca no existe en base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			estatusServicioService.deleteEstatusServicio(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar en base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Estatus Servicio eliminado con éxito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	// Crear
	@PostMapping("/estatusServicios")
	public ResponseEntity<?> create(@RequestBody EstatusServicioDto estatusServicio) {
		EstatusServicio estatusServicioNew = null;
		Map<String, Object> response = new HashMap<>();

		try {
			estatusServicioNew = this.estatusServicioService.createEstatusServicio(estatusServicio);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje",
				"Estatus Servicio creado con éxito, con el ID " + estatusServicioNew.getIdEstatusServicio());
		response.put("Estatus Servicio", estatusServicioNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	// Modificar
	@PutMapping("/estatusServicios/{id}")
	public ResponseEntity<?> modify(@PathVariable Long id, @RequestBody EstatusServicioDto estatusServicio) {
		EstatusServicio estatusServicioNew = null;
		Map<String, Object> response = new HashMap<>();

		try {
			estatusServicioNew = this.estatusServicioService.updateEstatusServicio(id, estatusServicio);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el update en base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje",
				"Estatus Servicio modificado con éxito, con el ID " + estatusServicioNew.getIdEstatusServicio());
		response.put("Estatus Servicio", estatusServicioNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
}