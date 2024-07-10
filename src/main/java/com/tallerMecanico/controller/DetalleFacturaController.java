package com.tallerMecanico.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tallerMecanico.dto.DetalleFacturaDto;
import com.tallerMecanico.entity.DetalleFactura;
import com.tallerMecanico.service.DetalleFacturaService;

@RestController
@CrossOrigin(origins = "http://localhost:4200", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping("/api")
public class DetalleFacturaController {
	@Autowired
	private DetalleFacturaService detalleFacturaService;

	// Eliminar por id
	@DeleteMapping("/detalleFactura/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();

		try {
			DetalleFactura detalleFacturaDelete = this.detalleFacturaService.findById(id);
			if (detalleFacturaDelete == null) {
				response.put("mensaje",
						"Error al eliminar. El Detalle de la Factura no existe en base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			detalleFacturaService.deleteDetalleFactura(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar en base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Detalle de la Factura eliminada con éxito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	// Crear
	@PostMapping("/detalleFactura")
	public ResponseEntity<?> create(@RequestBody DetalleFacturaDto detalleFactura) {
		DetalleFactura detalleFacturaNew = null;
		Map<String, Object> response = new HashMap<>();

		try {
			detalleFacturaNew = this.detalleFacturaService.createDetalleFactura(detalleFactura);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje",
				"Detalle de Factura creada con éxito, con el ID " + detalleFacturaNew.getIdDetalleFactura());
		response.put("Detalle de la Factura", detalleFacturaNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	// Modificar
	@PutMapping("/detalleFactura/{id}")
	public ResponseEntity<?> modify(@PathVariable Long id, @RequestBody DetalleFacturaDto detalleFactura) {
		DetalleFactura detalleFacturaNew = null;
		Map<String, Object> response = new HashMap<>();

		try {
			detalleFacturaNew = this.detalleFacturaService.updateDetalleFactura(id,
					detalleFactura);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el update en base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Detalle de Factura modificada con éxito, con el ID "
				+ detalleFacturaNew.getIdDetalleFactura());
		response.put("Detalle de Orden de Servicio", detalleFacturaNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

}
