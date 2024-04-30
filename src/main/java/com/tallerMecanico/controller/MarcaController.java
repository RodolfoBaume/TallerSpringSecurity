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

import com.tallerMecanico.dto.MarcaDto;
import com.tallerMecanico.entity.Marca;
import com.tallerMecanico.entity.Modelo;
import com.tallerMecanico.repository.IModeloRepository;
import com.tallerMecanico.service.MarcaService;

@RestController
@CrossOrigin(origins = "http://localhost:4200", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping("/api")
public class MarcaController {

	@Autowired
	private MarcaService marcaService;
	@Autowired
	private IModeloRepository modeloRepository;

	// Consulta todos
	@GetMapping("/marcas")
	@ResponseStatus(HttpStatus.OK)
	public List<Marca> consulta() {
		return marcaService.findAll();
	}

	// Consulta paginación
	@GetMapping("/marcas/page/{page}")
	public Page<Marca> consultaPage(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 10, Sort.by("idMarca").ascending());
		return marcaService.findAllPage(pageable);
	}

	// Consulta por id
	@GetMapping("/marcas/{id}")
	public ResponseEntity<?> consultaPorID(@PathVariable Long id) {

		Marca marca = null;
		String response = "";
		try {
			marca = marcaService.findById(id);
		} catch (DataAccessException e) {
			response = "Error al realizar la consulta.";
			response = response.concat(e.getMessage().concat(e.getMostSpecificCause().toString()));
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (marca == null) {
			response = "La marca con el ID: ".concat(id.toString()).concat(" no existe en la base de datos");
			return new ResponseEntity<String>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Marca>(marca, HttpStatus.OK);
	}

	// Consultar los modelos por marca
	@GetMapping("marcas/{idMarca}/modelos")
	public List<Modelo> getModelosByMarca(@PathVariable Long idMarca) {
		return modeloRepository.findByMarca_IdMarca(idMarca);
	}

	// Eliminar por id
	@DeleteMapping("/marcas/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();

		try {
			Marca marcaDelete = this.marcaService.findById(id);
			if (marcaDelete == null) {
				response.put("mensaje", "Error al eliminar. La marca no existe en base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			marcaService.deleteMarca(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar en base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Marca eliminada con éxito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	// Crear
	@PostMapping("/marcas")
	public ResponseEntity<?> create(@RequestBody MarcaDto marca) {
		Marca marcaNew = null;
		Map<String, Object> response = new HashMap<>();

		try {
			marcaNew = this.marcaService.createMarca(marca);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Marca creada con éxito, con el ID " + marcaNew.getIdMarca());
		response.put("Marca", marcaNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	// Modificar
	@PutMapping("/marcas/{id}")
	public ResponseEntity<?> modify(@PathVariable Long id, @RequestBody MarcaDto marca) {
		Marca marcaNew = null;
		Map<String, Object> response = new HashMap<>();

		try {
			marcaNew = this.marcaService.updateMarca(id, marca);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el update en base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Marca modificada con éxito, con el ID " + marcaNew.getIdMarca());
		response.put("marca", marcaNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
}
