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

import com.tallerMecanico.dto.OrdenServicioDto;
import com.tallerMecanico.dto.OrdenServicioVehiculoDto;
import com.tallerMecanico.entity.OrdenServicio;
import com.tallerMecanico.projection.IOrdenServicioDepto;
import com.tallerMecanico.projection.IOrdenServicioProjection;
import com.tallerMecanico.service.OrdenServicioService;

@RestController
@CrossOrigin(origins = "http://localhost:4200", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping("/api")
public class OrdenServicioController {

	@Autowired
	private OrdenServicioService ordenServicioService;
	
	@GetMapping("/ordenesServicio/{id}")
    public ResponseEntity<IOrdenServicioProjection> getOrdenServicioById(@PathVariable Long id) {
        IOrdenServicioProjection ordenServicio = ordenServicioService.getOrdenServicioById(id);
        return ResponseEntity.ok(ordenServicio);
    }

    @GetMapping("/ordenesServicio")
    public ResponseEntity<List<IOrdenServicioProjection>> getAllOrdenesServicio() {
        List<IOrdenServicioProjection> ordenesServicio = ordenServicioService.getAllOrdenesServicio();
        return ResponseEntity.ok(ordenesServicio);
    }


	// Consulta todos
	@GetMapping("/ordenesServicio2")
	@ResponseStatus(HttpStatus.OK)
	public List<OrdenServicio> consulta() {
		return ordenServicioService.findAll();
	}

	// Consulta todos
	@GetMapping("/ordenesServicio/vehiculo")
    public ResponseEntity<List<OrdenServicio>> consultarTodos() {
        List<OrdenServicio> ordenesServicio = ordenServicioService.buscarTodosConVehiculo();
        /*
        if (!ordenesServicio.isEmpty()) {
            return new ResponseEntity<>(ordenesServicio, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        */
        return new ResponseEntity<>(ordenesServicio, HttpStatus.OK);
    }
	
	// con vehiculo
	/*
	@GetMapping("/ordenesServicio")
    public ResponseEntity<List<OrdenServicioVehiculoDto>> getAllOrdenesServicioDTO() {
        List<OrdenServicioVehiculoDto> ordenesServicioDTO = ordenServicioService.getAllOrdenesServicioDTO();
        return ResponseEntity.ok(ordenesServicioDTO);
    }
    */

	// Consulta paginación
	/*
	@GetMapping("/ordenesServicio/page/{page}")
	public Page<OrdenServicio> consultaPage(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 10, Sort.by("idOrdenServicio").ascending());
		return ordenServicioService.findAllPage(pageable);
	}
	*/
	
	@GetMapping("/ordenesServicio/page/{page}")
    public ResponseEntity<Page<OrdenServicioVehiculoDto>> consultaPage(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("idOrdenServicio").ascending());
        Page<OrdenServicioVehiculoDto> ordenesServicioDTO = ordenServicioService.findAllPageDto(pageable);
        return ResponseEntity.ok(ordenesServicioDTO);
    }
	
	
	// Consulta por id
	/*
	@GetMapping("/ordenesServicio/{id}")
	public ResponseEntity<?> consultaPorID(@PathVariable Long id) {

		OrdenServicio ordenServicio = null;
		String response = "";
		try {
			ordenServicio = ordenServicioService.findById(id);
		} catch (DataAccessException e) {
			response = "Error al realizar la consulta.";
			response = response.concat(e.getMessage().concat(e.getMostSpecificCause().toString()));
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (ordenServicio == null) {
			response = "La Orden de Servicio con el ID: ".concat(id.toString())
					.concat(" no existe en la base de datos");
			return new ResponseEntity<String>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<OrdenServicio>(ordenServicio, HttpStatus.OK);
	}
*/
	
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

	@GetMapping("/ordenesServicio/estatus/{estatus}")
	public ResponseEntity<?> obtenerPorEstatus(@PathVariable String estatus) {
		List<OrdenServicio> servicios = null;
		Map<String, Object> response = new HashMap<>();
		try{
			servicios =  ordenServicioService.obtenerPorEstatusServicio(estatus);

		} catch(DataAccessException e){
			response.put("mensaje", "No ha sido posible obtener la lista de servicios por status.");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}

		return new ResponseEntity<List<OrdenServicio>>(servicios, HttpStatus.OK);

	
	}

	/*
	 * @GetMapping("/ordenesServicio/estatus/{estatus}") public
	 * ResponseEntity<List<OrdenServicio>> obtenerPorEstatus(@RequestParam String
	 * estatus) { List<OrdenServicio> ordenes =
	 * ordenServicioService.obtenerPorEstatusServicio(estatus); return
	 * ResponseEntity.ok(ordenes); }
	 */
	
	//Ordenes Servicio por Departamento
	@GetMapping("/ordenesServicio/departamento/{idDepartamento}")
	public List<IOrdenServicioDepto> getOrdenesServicioByDepartamento(@PathVariable Long idDepartamento) {
        return ordenServicioService.getOrdenesServicioByDepartamento(idDepartamento);
    }
	
	@GetMapping("/ordenesServicio/departamento2/{idDepartamento}")
    public List<OrdenServicio> getOrdenesByDepartamento(@PathVariable Long idDepartamento) {
        return ordenServicioService.getOrdenesByDepartamento(idDepartamento);
    }

}
