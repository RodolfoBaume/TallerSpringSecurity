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

import com.tallerMecanico.dto.VehiculoDto;
import com.tallerMecanico.entity.Vehiculo;
import com.tallerMecanico.service.VehiculoService;

@RestController
@CrossOrigin(origins = "http://localhost:4200", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping("/api")
public class VehiculoController {

	@Autowired
	private VehiculoService vehiculoService;

	// Consulta todos
	@GetMapping("/vehiculos")
	@ResponseStatus(HttpStatus.OK)
	public List<Vehiculo> consulta() {
		return vehiculoService.findAll();
	}

	// Consulta paginación
	@GetMapping("/vehiculos/page/{page}")
	public Page<Vehiculo> consultaPage(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 10, Sort.by("idVehiculo").ascending());
		return vehiculoService.findAllPage(pageable);
	}

	// Consulta por id
	@GetMapping("/vehiculos/{id}")
	public ResponseEntity<?> consultaPorID(@PathVariable Long id) {

		Vehiculo vehiculo = null;
		String response = "";
		try {
			vehiculo = vehiculoService.findById(id);
		} catch (DataAccessException e) {
			response = "Error al realizar la consulta.";
			response = response.concat(e.getMessage().concat(e.getMostSpecificCause().toString()));
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (vehiculo == null) {
			response = "el vehiculo con el ID: ".concat(id.toString()).concat(" no existe en la base de datos");
			return new ResponseEntity<String>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Vehiculo>(vehiculo, HttpStatus.OK);
	}

	// Eliminar por id
	@DeleteMapping("/vehiculos/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();

		try {
			Vehiculo vehiculoDelete = this.vehiculoService.findById(id);
			if (vehiculoDelete == null) {
				response.put("mensaje", "Error al eliminar. El vehiculo no existe en base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			vehiculoService.deleteVehiculo(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar en base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Vehiculo eliminado con éxito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	// Crear
	@PostMapping("/vehiculos")
	public ResponseEntity<?> create(@RequestBody VehiculoDto vehiculo) {
		Vehiculo vehiculoNew = null;
		Map<String, Object> response = new HashMap<>();

		try {
			vehiculoNew = this.vehiculoService.createVehiculo(vehiculo);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Vehiculo creado con éxito, con el ID " + vehiculoNew.getIdVehiculo());
		response.put("Vehiculo", vehiculoNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	// Modificar
	@PutMapping("/vehiculos/{id}")
	public ResponseEntity<?> modify(@PathVariable Long id, @RequestBody VehiculoDto vehiculo) {
		Vehiculo vehiculoNew = null;
		Map<String, Object> response = new HashMap<>();

		try {
			vehiculoNew = this.vehiculoService.updateVehiculo(id, vehiculo);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el update en base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Vehiculo modificado con éxito, con el ID " + vehiculoNew.getIdVehiculo());
		response.put("vehiculo", vehiculoNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
}
