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

import com.tallerMecanico.dto.ClienteDto;
import com.tallerMecanico.dto.RegistroResponseDto;
import com.tallerMecanico.dto.RegistroUsuarioClienteDto;
import com.tallerMecanico.entity.Cliente;
import com.tallerMecanico.entity.Vehiculo;
import com.tallerMecanico.repository.IVehiculoRepository;
import com.tallerMecanico.service.IClienteService;
import com.tallerMecanico.service.IUsuarioService;

@RestController
@CrossOrigin(origins = "http://localhost:4200", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping("/api")
public class ClienteController {

	@Autowired
	private IClienteService clienteService;
	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private IVehiculoRepository vehiculoRepository;

	// Consulta todos
	@GetMapping("/clientes")
	@ResponseStatus(HttpStatus.OK)
	public List<Cliente> consulta() {
		return clienteService.findAll();
	}

	// Consulta por id
	@GetMapping("/clientes/{id}")
	public ResponseEntity<?> consultaPorID(@PathVariable Long id) {

		Cliente cliente = null;
		String response = "";
		try {
			cliente = clienteService.findById(id);
		} catch (DataAccessException e) {
			response = "Error al realizar la consulta.";
			response = response.concat(e.getMessage().concat(e.getMostSpecificCause().toString()));
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (cliente == null) {
			response = "el Cliente con el ID: ".concat(id.toString()).concat(" no existe en la base de datos");
			return new ResponseEntity<String>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}

	// Consultar los vehiculos por cliente
	@GetMapping("clientes/{idCliente}/vehiculos")
	public List<Vehiculo> getVehiculosByCliente(@PathVariable Long idCliente) {
		return vehiculoRepository.findByCliente_IdCliente(idCliente);
	}


	// Eliminar por id
	@DeleteMapping("/clientes/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();

		try {
			Cliente clienteDelete = this.clienteService.findById(id);
			if (clienteDelete == null) {
				response.put("mensaje", "Error al eliminar. La marca no existe en base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			clienteService.deleteCliente(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar en base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Cliente eliminado con éxito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}


	// Crear
	@PostMapping("/clientes")
	public ResponseEntity<?> create(@RequestBody ClienteDto cliente) {
	    Cliente clienteNew = null;
	    Map<String, Object> response = new HashMap<>();

	    try {
	        // Asegúrate de obtener el idUsuario necesario aquí (puedes obtenerlo de donde proceda)
	        Long idUsuario = cliente.usuario() != null ? cliente.usuario().getIdUsuario() : null;

	        // Verifica si se obtuvo correctamente el idUsuario antes de llamar al servicio
	        if (idUsuario != null) {
	            clienteNew = this.clienteService.createCliente(cliente, idUsuario);

	            response.put("mensaje", "Cliente creado con éxito, con el ID " + clienteNew.getIdCliente());
	            response.put("Cliente", clienteNew);
	            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	        } else {
	            response.put("mensaje", "Error: Falta el ID de usuario en el clienteDto enviado");
	            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
	        }
	    } catch (DataAccessException e) {
	        response.put("mensaje", "Error al realizar el insert en la base de datos");
	        response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
	        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	// Modificar
	@PutMapping("/clientes/{id}")
	public ResponseEntity<?> modify(@PathVariable Long id, @RequestBody ClienteDto cliente) {
		Cliente clienteNew = null;
		Map<String, Object> response = new HashMap<>();

		try {
			clienteNew = this.clienteService.updateCliente(id, cliente);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el update en base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Cliente modificado con éxito, con el ID " + clienteNew.getIdCliente());
		response.put("cliente", clienteNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	// registrar usuario cliente
	@PostMapping("/registroUsuarioCliente")
	public ResponseEntity<?> registrarUsuarioYCliente(@RequestBody RegistroUsuarioClienteDto registroDto) {
	    Map<String, Object> response = new HashMap<>();

	    try {
	        ResponseEntity<RegistroResponseDto> registroUsuarioResponse = usuarioService.registrarUsuario(registroDto.getUsuario(), "CLIENTE");
	        RegistroResponseDto usuarioResponse = registroUsuarioResponse.getBody();

	        // Verificar si el ID de usuario no es nulo
	        if (usuarioResponse != null && usuarioResponse.getIdUsuario() != null) {
	            // Crear el cliente y asociarle el ID del usuario
	            Long idUsuario = usuarioResponse.getIdUsuario();
	            Cliente nuevoCliente = clienteService.createCliente(registroDto.getCliente(), idUsuario);

	            response.put("mensaje", "Usuario y cliente creados con éxito");
	            response.put("Usuario", usuarioResponse);
	            response.put("Cliente", nuevoCliente);
	            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	        } else {
	            response.put("mensaje", "Error al obtener el ID de usuario desde la respuesta");
	            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    } catch (DataAccessException e) {
	        response.put("mensaje", "Error al realizar la operación en la base de datos");
	        response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
	        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
}
