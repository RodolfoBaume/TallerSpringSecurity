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

import com.tallerMecanico.dto.EmpleadoDto;
import com.tallerMecanico.dto.RegistroResponseDto;
import com.tallerMecanico.dto.RegistroUsuarioEmpleadoDto;
import com.tallerMecanico.entity.Empleado;
import com.tallerMecanico.service.EmpleadoService;
import com.tallerMecanico.service.UsuarioAuthService;

@RestController
@CrossOrigin(origins = "http://localhost:4200", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping("/api")
public class EmpleadoController {

	@Autowired
	private EmpleadoService empleadoService;
	@Autowired
	private UsuarioAuthService usuarioService;

	// Consulta todos
	@GetMapping("/empleados")
	@ResponseStatus(HttpStatus.OK)
	public List<Empleado> consulta() {
		return empleadoService.findAll();
	}

	// Consulta paginación
	@GetMapping("/empleados/page/{page}")
	public Page<Empleado> consultaPage(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 10, Sort.by("idEmpleado").ascending());
		return empleadoService.findAllPage(pageable);
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

	// registrar usuario empleado
	@PostMapping("/empleados")
	public ResponseEntity<?> registrarUsuarioYEmpleado(@RequestBody RegistroUsuarioEmpleadoDto registroDto) {
		Map<String, Object> response = new HashMap<>();

		try {
			ResponseEntity<RegistroResponseDto> registroUsuarioResponse = usuarioService
					.registrarUsuario(registroDto.getUsuario(), "EMPLEADO");
			RegistroResponseDto usuarioResponse = registroUsuarioResponse.getBody();

			// Verificar si el ID de usuario no es nulo
			if (usuarioResponse != null && usuarioResponse.getIdUsuario() != null) {
				// Crear el empleado y asociarle el ID del usuario
				Long idUsuario = usuarioResponse.getIdUsuario();
				Empleado nuevoEmpleado = empleadoService.createEmpleado(registroDto.getEmpleado(), idUsuario);

				response.put("mensaje", "Usuario y empleado creados con éxito");
				response.put("Usuario", usuarioResponse);
				response.put("Empleado", nuevoEmpleado);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
			} else {
				response.put("mensaje", "Error al obtener el ID de usuario desde la respuesta, " + usuarioResponse.getMensaje());
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la operación en la base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Eliminar empleado
	@DeleteMapping("/empleados/{id}")
	public ResponseEntity<?> eliminarEmpleado(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			empleadoService.deleteEmpleado(id);
			response.put("mensaje", "Registro Eliminado");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (Error e) {
			response.put("mensaje", "Error al realizar la operación en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
	}

}
