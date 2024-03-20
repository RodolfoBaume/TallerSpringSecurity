package com.tallerMecanico.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

import com.tallerMecanico.dto.EmpleadoDto;
import com.tallerMecanico.entity.Empleado;
import com.tallerMecanico.service.IEmpleadoService;

@RestController
@CrossOrigin(origins = "http://localhost:4200", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping("/api")
public class EmpleadoController {

	@Autowired
	private IEmpleadoService empleadoService;

	// Consulta todos
	@GetMapping("/empleados")
	@ResponseStatus(HttpStatus.OK)
	public List<Empleado> consulta() {
		return empleadoService.findAll();
	}

	// Consulta por id
	@GetMapping("/empleados/{id}")
	public ResponseEntity<?> consultaPorID(@PathVariable Long id) {

		Empleado empleado = null;
		String response = "";
		try {
			empleado = empleadoService.findById(id);
		} catch (DataAccessException e) {
			response = "Error al realizar la consulta.";
			response = response.concat(e.getMessage().concat(e.getMostSpecificCause().toString()));
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (empleado == null) {
			response = "el Empleado con el ID: ".concat(id.toString()).concat(" no existe en la base de datos");
			return new ResponseEntity<String>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Empleado>(empleado, HttpStatus.OK);
	}

	// Eliminar por id
	@DeleteMapping("/empleados/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();

		try {
			Empleado empleadoDelete = this.empleadoService.findById(id);
			if (empleadoDelete == null) {
				response.put("mensaje", "Error al eliminar. La marca no existe en base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			empleadoService.deleteEmpleado(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar en base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Cliente eliminado con éxito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	// Crear
	@PostMapping("/empleados")
	public ResponseEntity<?> create(@RequestBody EmpleadoDto empleado) {
		Empleado empleadoNew = null;
		Map<String, Object> response = new HashMap<>();

		try {
			empleadoNew = this.empleadoService.createEmpleado(empleado);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Empleado creado con éxito, con el ID " + empleadoNew.getIdEmpleado());
		response.put("Empleado", empleadoNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	// Modificar
	@PutMapping("/empleados/{id}")
	public ResponseEntity<?> modify(@PathVariable Long id, @RequestBody EmpleadoDto empleado) {
		Empleado empleadoNew = null;
		Map<String, Object> response = new HashMap<>();

		try {
			empleadoNew = this.empleadoService.updateEmpleado(id, empleado);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el update en base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Empleado modificado con éxito, con el ID " + empleadoNew.getIdEmpleado());
		response.put("empleado", empleadoNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
}
