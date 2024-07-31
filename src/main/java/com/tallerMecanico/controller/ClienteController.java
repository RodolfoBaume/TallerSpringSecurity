package com.tallerMecanico.controller;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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


import com.tallerMecanico.dto.ClienteDto;
import com.tallerMecanico.dto.RegistroResponseDto;
import com.tallerMecanico.dto.RegistroUsuarioClienteDto;
import com.tallerMecanico.entity.Cliente;
import com.tallerMecanico.projection.IClienteClosedView;
import com.tallerMecanico.projection.IClienteProjection;
import com.tallerMecanico.projection.IVehiculoClosedView;
import com.tallerMecanico.repository.IVehiculoRepository;
import com.tallerMecanico.service.ClienteService;
import com.tallerMecanico.service.UsuarioAuthService;


@RestController
@CrossOrigin(origins = "http://localhost:4200", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping("/api")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;
	//@Autowired
	//private IUsuarioService usuarioService;
	@Autowired
	private UsuarioAuthService usuarioService;

	
	@Autowired
	private IVehiculoRepository vehiculoRepository;

	
	// Consulta todos
	@GetMapping("/clientes")
	@ResponseStatus(HttpStatus.OK)
	public List<Cliente> consulta() {
		return clienteService.findAll();
	}
	
	@GetMapping("/clientes/vehiculos")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<IClienteClosedView>> getAllClientesWithVehiculos() {
        List<IClienteClosedView> clientes = clienteService.getAllClientesWithVehiculos();
        return ResponseEntity.ok(clientes);
    }
	
	// paginacion con vehiculo
	@GetMapping("/clientes/page/{page}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<IClienteClosedView>> getAllClientesWithVehiculos(
        @PathVariable int page, 
        @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("idCliente").ascending());
        Page<IClienteClosedView> clientes = clienteService.getAllClientesWithVehiculos(pageable);
        return ResponseEntity.ok(clientes);
    }
	
	

	// Consulta paginación
	@GetMapping("/clientes/opcion2/page/{page}")
	public Page<Cliente> consultaPage(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 10, Sort.by("idCliente").ascending());
		return clienteService.findAllPage(pageable);
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
	public List<IVehiculoClosedView> getVehiculosByCliente(@PathVariable Long idCliente) {
		return vehiculoRepository.findByCliente_IdCliente(idCliente);
	}

	
	// Eliminar por id
	/*
	 * @DeleteMapping("/clientes/{id}") public ResponseEntity<?>
	 * delete(@PathVariable Long id) {
	 * 
	 * Map<String, Object> response = new HashMap<>();
	 * 
	 * try { Cliente clienteDelete = this.clienteService.findById(id); if
	 * (clienteDelete == null) { response.put("mensaje",
	 * "Error al eliminar. La marca no existe en base de datos"); return new
	 * ResponseEntity<Map<String, Object>>(response,
	 * HttpStatus.INTERNAL_SERVER_ERROR); }
	 * 
	 * clienteService.deleteCliente(id); } catch (DataAccessException e) {
	 * response.put("mensaje", "Error al eliminar en base de datos");
	 * response.put("error",
	 * e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
	 * return new ResponseEntity<Map<String, Object>>(response,
	 * HttpStatus.INTERNAL_SERVER_ERROR); } response.put("mensaje",
	 * "Cliente eliminado con éxito"); return new ResponseEntity<Map<String,
	 * Object>>(response, HttpStatus.OK); }
	 */

	// Crear
	/*
	 * @PostMapping("/clientes") public ResponseEntity<?> create(@RequestBody
	 * ClienteDto cliente) { Cliente clienteNew = null; Map<String, Object> response
	 * = new HashMap<>();
	 * 
	 * try { // Asegúrate de obtener el idUsuario necesario aquí (puedes obtenerlo
	 * de donde proceda) Long idUsuario = cliente.usuario() != null ?
	 * cliente.usuario().getIdUsuario() : null;
	 * 
	 * // Verifica si se obtuvo correctamente el idUsuario antes de llamar al
	 * servicio if (idUsuario != null) { clienteNew =
	 * this.clienteService.createCliente(cliente, idUsuario);
	 * 
	 * response.put("mensaje", "Cliente creado con éxito, con el ID " +
	 * clienteNew.getIdCliente()); response.put("Cliente", clienteNew); return new
	 * ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED); } else {
	 * response.put("mensaje",
	 * "Error: Falta el ID de usuario en el clienteDto enviado"); return new
	 * ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST); } }
	 * catch (DataAccessException e) { response.put("mensaje",
	 * "Error al realizar el insert en la base de datos"); response.put("error",
	 * e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
	 * return new ResponseEntity<Map<String, Object>>(response,
	 * HttpStatus.INTERNAL_SERVER_ERROR); } }
	 */

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

	// crear Cliente
	// registrar usuario cliente
	// @PostMapping("/registroUsuarioCliente")
	@PostMapping("/clientes")
	public ResponseEntity<?> registrarUsuarioYCliente(@RequestBody RegistroUsuarioClienteDto registroDto) {
		Map<String, Object> response = new HashMap<>();

		try {
			ResponseEntity<RegistroResponseDto> registroUsuarioResponse = usuarioService
					.registrarUsuario(registroDto.getUsuario(), "CLIENTE");
					
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
				response.put("mensaje", "Error al obtener el ID de usuario desde la respuesta, "+ usuarioResponse.getMensaje());
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la operación en la base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Eliminar cliente
	@DeleteMapping("/clientes/{id}")
	public ResponseEntity<String> eliminarCliente(@PathVariable Long id) {
		try {
			clienteService.deleteCliente(id);
			return new ResponseEntity<>("Cliente eliminado exitosamente", HttpStatus.OK);
		} catch (Error e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	// Endpoint para buscar clientes por nombre, apellido paterno, apellido materno o teléfono
    @GetMapping("/clientes/buscar")
    public List<Cliente> buscarClientes(@RequestParam String searchTerm) {
        return clienteService.buscarClientesPorNombreApellidoPaternoApellidoMaternoTelefono(searchTerm);
    }
    
    
    @GetMapping("clientes/pdf")
    public ResponseEntity<byte[]> generarReporteClientes(
            @RequestParam(defaultValue = "nombre") String orderBy,
            @RequestParam(defaultValue = "asc") String orderDirection) throws IOException {
        
        List<IClienteProjection> clientes = clienteService.getAllClientes(orderBy, orderDirection);

        byte[] pdfBytes = clienteService.generarPDF(clientes);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "reporteClientes.pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
 
    
}
