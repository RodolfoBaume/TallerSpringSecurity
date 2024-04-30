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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tallerMecanico.dto.RegistroResponseDto;
import com.tallerMecanico.dto.UsuarioDto;
import com.tallerMecanico.entity.Usuario;
import com.tallerMecanico.service.UsuarioService;

@RestController
@CrossOrigin(origins = "http://localhost:4200", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping("/api")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	// Consulta todos
	@GetMapping("/usuarios")
	@ResponseStatus(HttpStatus.OK)
	public List<Usuario> consulta() {
		return usuarioService.findAll();
	}

	// Consulta paginación
	@GetMapping("/usuarios/page/{page}")
	public Page<Usuario> consultaPage(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 10, Sort.by("idUsuario").ascending());
		return usuarioService.findAllPage(pageable);
	}

	// Consulta por id
	@GetMapping("/usuarios/{id}")
	public ResponseEntity<?> consultaPorID(@PathVariable Long id) {

		Usuario usuario = null;
		String response = "";
		try {
			usuario = usuarioService.findById(id);
		} catch (DataAccessException e) {
			response = "Error al realizar la consulta.";
			response = response.concat(e.getMessage().concat(e.getMostSpecificCause().toString()));
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (usuario == null) {
			response = "El Usuario con el ID: ".concat(id.toString()).concat(" no existe en la base de datos");
			return new ResponseEntity<String>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}

	// Eliminar por id
	@DeleteMapping("/usuarios/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();

		try {
			Usuario usuarioDelete = this.usuarioService.findById(id);
			if (usuarioDelete == null) {
				response.put("mensaje", "Error al eliminar. La marca no existe en base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			usuarioService.deleteUsuario(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar en base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Usuario eliminado con éxito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	/*
	 * // Crear
	 * 
	 * @PostMapping("/usuarios") public ResponseEntity<?> create(@RequestBody
	 * UsuarioDto usuario) { Usuario usuarioNew = null; Map<String, Object> response
	 * = new HashMap<>();
	 * 
	 * try { usuarioNew = this.usuarioService.createUsuario(usuario); } catch
	 * (DataAccessException e) { response.put("mensaje",
	 * "Error al realizar el insert en base de datos"); response.put("error",
	 * e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
	 * return new ResponseEntity<Map<String, Object>>(response,
	 * HttpStatus.INTERNAL_SERVER_ERROR); }
	 * 
	 * response.put("mensaje", "Usuario creado con éxito, con el ID " +
	 * usuarioNew.getIdUsuario()); response.put("Usuario", usuarioNew); return new
	 * ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED); }
	 */

	// Método para poder registrar usuarios con role "user"
	@PostMapping("/auth/register")
	public ResponseEntity<RegistroResponseDto> registrarUsuario(@RequestBody UsuarioDto dtoRegistro,
			@RequestParam String role) {
		return usuarioService.registrarUsuario(dtoRegistro, role);
	}

	// Modificar
	@PutMapping("/usuarios/{id}")
	public ResponseEntity<?> modify(@PathVariable Long id, @RequestBody UsuarioDto usuario) {
		Usuario usuarioNew = null;
		Map<String, Object> response = new HashMap<>();

		try {
			usuarioNew = this.usuarioService.updateUsuario(id, usuario);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el update en base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Usuario modificado con éxito, con el ID " + usuarioNew.getIdUsuario());
		response.put("usuario", usuarioNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
}