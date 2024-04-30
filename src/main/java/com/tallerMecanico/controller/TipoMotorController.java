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

import com.tallerMecanico.dto.TipoMotorDto;
import com.tallerMecanico.entity.TipoMotor;
import com.tallerMecanico.service.TipoMotorService;

@RestController
@CrossOrigin(origins = "http://localhost:4200", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping("/api")
public class TipoMotorController {

	@Autowired
	private TipoMotorService tipoMotorService;

	// Consulta todos
	@GetMapping("/tiposMotor")
	@ResponseStatus(HttpStatus.OK)
	public List<TipoMotor> consulta() {
		return tipoMotorService.findAll();
	}

	// Consulta paginación
	@GetMapping("/tiposMotor/page/{page}")
	public Page<TipoMotor> consultaPage(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 10, Sort.by("idTipoMotor").ascending());
		return tipoMotorService.findAllPage(pageable);
	}

	// Consulta por id
	@GetMapping("/tiposMotor/{id}")
	public ResponseEntity<?> consultaPorID(@PathVariable Long id) {

		TipoMotor tipoMotor = null;
		String response = "";
		try {
			tipoMotor = tipoMotorService.findById(id);
		} catch (DataAccessException e) {
			response = "Error al realizar la consulta.";
			response = response.concat(e.getMessage().concat(e.getMostSpecificCause().toString()));
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (tipoMotor == null) {
			response = "el Tipo de Motor con el ID: ".concat(id.toString()).concat(" no existe en la base de datos");
			return new ResponseEntity<String>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<TipoMotor>(tipoMotor, HttpStatus.OK);
	}

	// Eliminar por id
	@DeleteMapping("/tiposMotor/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();

		try {
			TipoMotor tipoMotorDelete = this.tipoMotorService.findById(id);
			if (tipoMotorDelete == null) {
				response.put("mensaje", "Error al eliminar. El tipo de motor no existe en base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			tipoMotorService.deleteTipoMotor(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar en base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Tipo de Motor eliminado con éxito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	// Crear
	@PostMapping("/tiposMotor")
	public ResponseEntity<?> create(@RequestBody TipoMotorDto tipoMotor) {
		TipoMotor tipoMotorNew = null;
		Map<String, Object> response = new HashMap<>();

		try {
			tipoMotorNew = this.tipoMotorService.createTipoMotor(tipoMotor);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Tipo de Motor creado con éxito, con el ID " + tipoMotorNew.getIdTipoMotor());
		response.put("Tipo de motor", tipoMotorNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	// Modificar
	@PutMapping("/tiposMotor/{id}")
	public ResponseEntity<?> modify(@PathVariable Long id, @RequestBody TipoMotorDto tipoMotor) {
		TipoMotor tipoMotorNew = null;
		Map<String, Object> response = new HashMap<>();

		try {
			tipoMotorNew = this.tipoMotorService.updateTipoMotor(id, tipoMotor);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el update en base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Tipo de Motor modificado con éxito, con el ID " + tipoMotorNew.getIdTipoMotor());
		response.put("Tipo de motor", tipoMotorNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
}
