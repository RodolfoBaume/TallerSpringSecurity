package com.tallerMecanico.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tallerMecanico.dto.OrdenServicioDto;
import com.tallerMecanico.entity.OrdenServicio;
import com.tallerMecanico.projection.IOrdenServicioDepto;
import com.tallerMecanico.projection.IOrdenServicioProjection;
import com.tallerMecanico.projection.IOrdenServicioSinDetalle;
import com.tallerMecanico.repository.IOrdenServicioRepository;
import com.tallerMecanico.service.OrdenServicioService;

@RestController
@CrossOrigin(origins = "http://localhost:4200", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping("/api")
public class OrdenServicioController {

	@Autowired
	private OrdenServicioService ordenServicioService;
	
	@Autowired
	private IOrdenServicioRepository ordenServicioRepository;

	@GetMapping("/ordenesServicio/{id}")
	public IOrdenServicioProjection getOrdenServicioById(@PathVariable("id") long ordenServicioId) {
		// TODO: no trae datos del empleado
		return ordenServicioService.getOrdenServicioById(ordenServicioId);
	}

	@GetMapping("/ordenesServicio")
	public List<IOrdenServicioSinDetalle> getAllOrdenesServicio() {
		return ordenServicioService.getAllOrdenesServicio();
	}

	// Consulta todos
	@GetMapping("/ordenesServicio2")
	@ResponseStatus(HttpStatus.OK)
	public List<OrdenServicio> consulta() {
		return ordenServicioService.findAll();
	}

	// Consulta paginación
	@GetMapping("/ordenesServicio/page/{page}")
	public ResponseEntity<Slice<IOrdenServicioSinDetalle>> consultaPage2(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 10, Sort.by("idOrdenServicio").ascending());
		Slice<IOrdenServicioSinDetalle> result = ordenServicioService.getAllOrdenesServicio(pageable);
		return ResponseEntity.ok(result);
	}


	// Eliminar por id
	@DeleteMapping("/ordenesServicio/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();

		try {
			OrdenServicio ordenServicioDelete = this.ordenServicioService.findById(id);
			if (ordenServicioDelete == null) {
				response.put("mensaje", "Error al eliminar. La Orden de Servicio no existe en base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			ordenServicioService.deleteOrdenServicio(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar en base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Orden de Servicio eliminada con éxito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	// Crear
	@PostMapping("/ordenesServicio")
	public ResponseEntity<?> create(@RequestBody OrdenServicioDto ordenServicio) {
		OrdenServicio ordenServicioNew = null;
		Map<String, Object> response = new HashMap<>();

		try {
			ordenServicioNew = this.ordenServicioService.createOrdenServicio(ordenServicio);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje",
				"Orden de Servicio creada con éxito, con el ID " + ordenServicioNew.getIdOrdenServicio());
		response.put("ordenServicio", ordenServicioNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	// Modificar
	@PutMapping("/ordenesServicio/{id}")
	public ResponseEntity<?> modify(@PathVariable Long id, @RequestBody OrdenServicioDto ordenServicio) {
		OrdenServicio ordenServicioNew = null;
		Map<String, Object> response = new HashMap<>();

		try {
			ordenServicioNew = this.ordenServicioService.updateOrdenServicio(id, ordenServicio);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el update en base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje",
				"Orden de Servicio modificada con éxito, con el ID " + ordenServicioNew.getIdOrdenServicio());
		response.put("ordenServicio", ordenServicioNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	// filtar por estatus de servicio
	@GetMapping("/ordenesServicio/estatus/{estatus}")
	public ResponseEntity<?> obtenerPorEstatus(@PathVariable String estatus) {
		List<IOrdenServicioDepto> servicios = null;
		Map<String, Object> response = new HashMap<>();
		try {
			servicios = ordenServicioService.obtenerPorEstatusServicio(estatus);

		} catch (DataAccessException e) {
			response.put("mensaje", "No ha sido posible obtener la lista de servicios por status.");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}

		return new ResponseEntity<List<IOrdenServicioDepto>>(servicios, HttpStatus.OK);

	}
	
	// paginacion por estatus
	@GetMapping("/ordenesServicio/estatus/{estatus}/page/{page}")
	public ResponseEntity<Page<IOrdenServicioDepto>> consultaPageStatus(@PathVariable String estatus, @PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 10, Sort.by("idOrdenServicio").ascending());
		Page<IOrdenServicioDepto> result = ordenServicioService.obtenerPorEstatusServicio(estatus, pageable);
		return ResponseEntity.ok(result);
	}

	// Ordenes Servicio por Departamento
	@GetMapping("/ordenesServicio/departamento/{idDepartamento}")
	public List<IOrdenServicioDepto> getOrdenesServicioByDepartamento(@PathVariable Long idDepartamento) {
		return ordenServicioService.getOrdenesServicioByDepartamento(idDepartamento, Sort.by(Sort.Direction.DESC, "idOrdenServicio"));
	}
	
	// Paginacion Ordenes Servicio por Departamento
	@GetMapping("/ordenesServicio/departamento/{idDepartamento}/page/{page}")
	public Page<IOrdenServicioDepto> getOrdenesServicioByDepartamento(@PathVariable Long idDepartamento, @PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 10, Sort.by("idOrdenServicio").descending());
		return ordenServicioService.getOrdenesServicioByDepartamento(idDepartamento, pageable);
	}
	
	@GetMapping("/ordenesServicio/pdf")
	public ResponseEntity<byte[]> generarReporteOrdenesServicio() throws IOException {
	    List<IOrdenServicioProjection> ordenesServicio = new ArrayList<>();
	    
	    // Obtener todos los IDs de las órdenes de servicio
	    List<Long> idsOrdenesServicio = ordenServicioRepository.findAllIds();
	    
	    for (Long id : idsOrdenesServicio) {
	        IOrdenServicioProjection orden = ordenServicioService.findProjectedById(id);
	        ordenesServicio.add(orden);
	    }

	    byte[] pdfBytes = ordenServicioService.generarPDF(ordenesServicio);
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_PDF);
	    headers.setContentDispositionFormData("inline", "reporteOrdenesServicio.pdf");
	    return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
	}

}
