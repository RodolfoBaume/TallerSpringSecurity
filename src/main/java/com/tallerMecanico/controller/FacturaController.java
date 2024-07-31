package com.tallerMecanico.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.tallerMecanico.dto.FacturaDto;
import com.tallerMecanico.dto.FacturaOrdenDto;
import com.tallerMecanico.dto.ReporteMesesDto;
import com.tallerMecanico.entity.Factura;
import com.tallerMecanico.projection.IFacturaClosedView;
import com.tallerMecanico.projection.IFacturaReporte;
import com.tallerMecanico.service.FacturaService;

@RestController
@CrossOrigin(origins = "http://localhost:4200", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping("/api")
public class FacturaController {

	@Autowired
	private FacturaService facturaService;

	



	// Consulta paginación
	@GetMapping("/facturas/page/{page}")
	public Page<IFacturaClosedView> consultaPage(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 10, Sort.by("idFactura").ascending());
		return facturaService.getAllFacturasWithDetalle(pageable);
	}
	
	@GetMapping("/facturas")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<IFacturaClosedView>> getAllFacturasWithDetalle() {
        List<IFacturaClosedView> facturas = facturaService.getAllFacturasWithDetalle();
        return ResponseEntity.ok(facturas);
    }

	// Consulta por id
	/*
	@GetMapping("/facturas/{id}")
	public ResponseEntity<?> consultaPorID(@PathVariable Long id) {

		Factura factura = null;
		String response = "";
		try {
			factura = facturaService.findById(id);
		} catch (DataAccessException e) {
			response = "Error al realizar la consulta.";
			response = response.concat(e.getMessage().concat(e.getMostSpecificCause().toString()));
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (factura == null) {
			response = "La Factura con el ID: ".concat(id.toString()).concat(" no existe en la base de datos");
			return new ResponseEntity<String>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Factura>(factura, HttpStatus.OK);
	}
	
	*/
	
	@GetMapping("/facturas/{idFactura}")
	public ResponseEntity<?> getFacturaById(@PathVariable Long idFactura) {
	    try {
	        IFacturaClosedView factura = facturaService.getFacturaWithDetalleById(idFactura);

	        if (factura == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Factura no encontrada con id: " + idFactura);
	        }

	        return ResponseEntity.ok(factura);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener la factura: " + e.getMessage());
	    }
	}
	
	

	// Eliminar por id
	@DeleteMapping("/facturas/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();

		try {
			Factura facturaDelete = this.facturaService.findById(id);
			if (facturaDelete == null) {
				response.put("mensaje", "Error al eliminar. La factura no existe en base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			facturaService.deleteFactura(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar en base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Factura eliminada con éxito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	// Crear por OrdenServicio
	@PostMapping("/facturas/{ordenServicioId}")
	public ResponseEntity<FacturaOrdenDto> crearFactura(@PathVariable long ordenServicioId) {
        FacturaOrdenDto facturaDTO = facturaService.crearFactura(ordenServicioId);
        return new ResponseEntity<>(facturaDTO, HttpStatus.CREATED);
    }
	
	// Crear
	@PostMapping("/facturas")
	public ResponseEntity<?> create(@RequestBody FacturaDto factura) {
		Factura facturaNew = null;
		Map<String, Object> response = new HashMap<>();

		try {
			facturaNew = this.facturaService.createFactura(factura);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Factura creada con éxito, con el ID " + facturaNew.getIdFactura());
		response.put("Factura", facturaNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	// Modificar
	@PutMapping("/facturas/{id}")
	public ResponseEntity<?> modify(@PathVariable Long id, @RequestBody FacturaDto factura) {
		Factura facturaNew = null;
		Map<String, Object> response = new HashMap<>();

		try {
			facturaNew = this.facturaService.updateFactura(id, factura);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el update en base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Factura modificada con éxito, con el ID " + facturaNew.getIdFactura());
		response.put("Factura", facturaNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/facturas/pdf")
    public ResponseEntity<byte[]> generarReporteFacturacion(
            @RequestParam("startDate") String startDateStr,
            @RequestParam("endDate") String endDateStr) throws IOException, ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse(startDateStr);
        Date endDate = sdf.parse(endDateStr);

        List<IFacturaReporte> facturas = facturaService.getFacturasByDateRange(startDate, endDate);

        byte[] pdfBytes = facturaService.generarPDF(facturas, startDate, endDate);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "reporteFacturacion.pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
	@GetMapping("/facturas/por-meses")
	public List<?> obtenerTotalPorMeses(){

		return facturaService.obtenerTotalPorMeses();

	}
}
