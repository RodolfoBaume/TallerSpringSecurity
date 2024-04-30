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

import com.tallerMecanico.dto.RolDto;
import com.tallerMecanico.entity.Rol;
import com.tallerMecanico.service.RolService;

@RestController
@CrossOrigin(origins = "http://localhost:4200", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping("/api")
public class RolController {

	@Autowired
	private RolService rolService;

	// Consulta todos
	@GetMapping("/roles")
	@ResponseStatus(HttpStatus.OK)
	public List<Rol> consulta() {
		return rolService.findAll();
	}

	// Consulta paginación
	@GetMapping("/roles/page/{page}")
	public Page<Rol> consultaPage(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 10, Sort.by("idRol").ascending());
		return rolService.findAllPage(pageable);
	}

	// Consulta por id
	@GetMapping("/roles/{id}")
	public ResponseEntity<?> consultaPorID(@PathVariable Long id) {

		Rol rol = null;
		String response = "";
		try {
			rol = rolService.findById(id);
		} catch (DataAccessException e) {
			response = "Error al realizar la consulta.";
			response = response.concat(e.getMessage().concat(e.getMostSpecificCause().toString()));
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (rol == null) {
			response = "El Rol con el ID: ".concat(id.toString()).concat(" no existe en la base de datos");
			return new ResponseEntity<String>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Rol>(rol, HttpStatus.OK);
	}

	// Eliminar por id
	@DeleteMapping("/roles/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();

		try {
			Rol rolDelete = this.rolService.findById(id);
			if (rolDelete == null) {
				response.put("mensaje", "Error al eliminar. La marca no existe en base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			rolService.deleteRol(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar en base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Rol eliminado con éxito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	// Crear
	@PostMapping("/roles")
	public ResponseEntity<?> create(@RequestBody RolDto rol) {
		Rol rolNew = null;
		Map<String, Object> response = new HashMap<>();

		try {
			rolNew = this.rolService.createRol(rol);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Rol creado con éxito, con el ID " + rolNew.getIdRol());
		response.put("Rol", rolNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	// Modificar
	@PutMapping("/roles/{id}")
	public ResponseEntity<?> modify(@PathVariable Long id, @RequestBody RolDto rol) {
		Rol rolNew = null;
		Map<String, Object> response = new HashMap<>();

		try {
			rolNew = this.rolService.updateRol(id, rol);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el update en base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Rol modificado con éxito, con el ID " + rolNew.getIdRol());
		response.put("rol", rolNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
}
