package com.tallerMecanico.controller;

import java.io.IOException;
import java.security.Principal;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.tallerMecanico.dto.UsuarioActualDto;
import com.tallerMecanico.dto.VehiculoDto;
import com.tallerMecanico.entity.Vehiculo;
import com.tallerMecanico.projection.IVehiculoConOrdenClosedView;
import com.tallerMecanico.projection.IVehiculoReporte;
import com.tallerMecanico.projection.IVehiculoSinOrden;
import com.tallerMecanico.service.UsuarioAuthService;
import com.tallerMecanico.service.VehiculoService;

@RestController
@CrossOrigin(origins = "http://localhost:4200", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping("/api")
public class VehiculoController {

	@Autowired
	private VehiculoService vehiculoService;
	@Autowired
	private UsuarioAuthService usuarioAuthService;

	// Consulta todos
	/*
	 * @GetMapping("/vehiculos")
	 * 
	 * @ResponseStatus(HttpStatus.OK) public List<Vehiculo> consulta() { return
	 * vehiculoService.findAll(); }
	 */

	@GetMapping("/vehiculos")
	@ResponseStatus(HttpStatus.OK)
	public List<IVehiculoConOrdenClosedView> consulta() {
		return vehiculoService.findBy();
	}

	// Consulta paginación nueva con proyeccion
	@GetMapping("/vehiculos/page/{page}")
	public Page<IVehiculoConOrdenClosedView> consultaPage(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 10, Sort.by("idVehiculo").descending());
		return vehiculoService.findBy(pageable);
	}

	@GetMapping("/vehiculos/{id}")
	public IVehiculoConOrdenClosedView getVehiculoById(@PathVariable("id") Long idVehiculo) {
		return vehiculoService.findByIdVehiculo(idVehiculo);
	}

	@GetMapping("/vehiculos/{id}/cliente")
	public IVehiculoConOrdenClosedView getVehiculoById(@PathVariable("id") Long idVehiculo, Principal principal) {
		// Obtener el idCliente del usuario logueado
		ResponseEntity<UsuarioActualDto> currentUser = usuarioAuthService.obtenerUsuarioActual(principal);
		Long idCliente = currentUser.getBody().getIdCliente();

		// Usar el idCliente para filtrar los vehículos
		return vehiculoService.findByIdVehiculoAndClienteId(idVehiculo, idCliente);
	}

	// Consulta todos
	/*
	 * @GetMapping("/vehiculos/orden")
	 * 
	 * @ResponseStatus(HttpStatus.OK) public List<Vehiculo> consultas() { return
	 * vehiculoService.obtenerTodosLosVehiculosConOrdenServicio(); }
	 */

	// Consulta paginación
	/*
	 * @GetMapping("/vehiculos/page/{page}") public Page<Vehiculo>
	 * consultaPage(@PathVariable Integer page) { Pageable pageable =
	 * PageRequest.of(page, 10, Sort.by("idVehiculo").ascending()); return
	 * vehiculoService.findAllPage(pageable); }
	 */

	// Consulta por id
	/*
	 * @GetMapping("/vehiculos/{id}") public ResponseEntity<?>
	 * consultaPorID(@PathVariable Long id) {
	 * 
	 * Vehiculo vehiculo = null; String response = ""; try { vehiculo =
	 * vehiculoService.findById(id); } catch (DataAccessException e) { response =
	 * "Error al realizar la consulta."; response =
	 * response.concat(e.getMessage().concat(e.getMostSpecificCause().toString()));
	 * return new ResponseEntity<String>(response,
	 * HttpStatus.INTERNAL_SERVER_ERROR); }
	 * 
	 * if (vehiculo == null) { response =
	 * "el vehiculo con el ID: ".concat(id.toString()).
	 * concat(" no existe en la base de datos"); return new
	 * ResponseEntity<String>(response, HttpStatus.NOT_FOUND); } return new
	 * ResponseEntity<Vehiculo>(vehiculo, HttpStatus.OK); }
	 */

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
		response.put("Vehiculo", vehiculoNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	// Filtra Vehiculos en taller, sin entregar
	/*
	@GetMapping("/vehiculos/noEntregados")
	public List<IVehiculoConOrdenClosedView> getVehiculosNoEntregados(
			@RequestParam(defaultValue = "r. Entregado") String estatus) {
		return vehiculoService.getVehiculosByOrdenServicioEstatus(estatus);
	}
	*/
	
	@GetMapping("/vehiculos/noEntregados")
	public List<IVehiculoConOrdenClosedView> getVehiculosNoEntregados(
	        @RequestParam(defaultValue = "r. Entregado") String estatus) {
	    return vehiculoService.getVehiculosByOrdenServicioEstatus(estatus, Sort.by(Sort.Direction.DESC, "idVehiculo"));
	}

	// Filtra Vehiculos en taller, sin entregar (con paginacion)
	@GetMapping("/vehiculos/noEntregados/page/{page}")
	public Page<IVehiculoConOrdenClosedView> getVehiculosNoEntregados(
			@RequestParam(defaultValue = "r. Entregado") String estatus, @PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 10, Sort.by("idVehiculo").descending());
		return vehiculoService.getVehiculosByOrdenServicioEstatus(estatus, pageable);
	}

	@GetMapping("/vehiculos/pdf")
	public ResponseEntity<byte[]> generarReporteVehiculos(

			@RequestParam(required = false) Integer anioModelo,

			@RequestParam(required = false) String marca) throws IOException {
		List<IVehiculoSinOrden> vehiculos = vehiculoService.getAllVehiculos(anioModelo, marca);

		byte[] pdfBytes = vehiculoService.generarPDF1(vehiculos, null, null);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDispositionFormData("inline", "reporteVehiculos.pdf");

		return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
	}

	/*
	 * @GetMapping("/vehiculos/atendidosPdf") public ResponseEntity<byte[]>
	 * generarReporteVehiculosAtendidos(
	 * 
	 * @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaInicio,
	 * 
	 * @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaFin)
	 * throws IOException { List<IVehiculoSinOrden> vehiculos =
	 * vehiculoService.getVehiculosAtendidosPorPeriodo(fechaInicio, fechaFin);
	 * 
	 * byte[] pdfBytes = vehiculoService.generarPDF(vehiculos, fechaInicio,
	 * fechaFin);
	 * 
	 * HttpHeaders headers = new HttpHeaders();
	 * headers.setContentType(MediaType.APPLICATION_PDF);
	 * headers.setContentDispositionFormData("inline",
	 * "reporteVehiculosAtendidos.pdf");
	 * 
	 * return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK); }
	 */

	@GetMapping("/vehiculos/reporte")
	public ResponseEntity<List<IVehiculoReporte>> generarReporte(
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin) {
		List<IVehiculoReporte> reporte = vehiculoService.obtenerReporteVehiculos(fechaInicio, fechaFin);
		return ResponseEntity.ok(reporte);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<String> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
		return ResponseEntity.badRequest().body("Error de tipo: " + ex.getMessage());
	}

	@GetMapping("/vehiculos/atendidosPdf")
	public ResponseEntity<byte[]> generarReportePdf(
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin) throws IOException {

		List<IVehiculoReporte> vehiculos = vehiculoService.obtenerReporteVehiculos(fechaInicio, fechaFin);
		byte[] pdfBytes = vehiculoService.generarPDF(vehiculos,
				fechaInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
				fechaFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDispositionFormData("inline", "reporteClientes.pdf");

		return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

		/*
		 * return ResponseEntity.ok() .header("Content-Disposition",
		 * "attachment; filename=reporte_vehiculos.pdf") .body(pdfBytes);
		 */
	}

}
