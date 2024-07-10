package com.tallerMecanico.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tallerMecanico.dto.DetalleFacturaDto;
import com.tallerMecanico.entity.DetalleFactura;
import com.tallerMecanico.repository.IDetalleFacturaRepository;

@Service
public class DetalleFacturaService implements IDetalleFacturaService {

	@Autowired
	private IDetalleFacturaRepository detalleFacturaRepository;

	// consulta por id
	@Transactional(readOnly = true)
	public DetalleFactura findById(Long idDetalleFactura) {
		return detalleFacturaRepository.findById(idDetalleFactura).orElse(null);
	}

	// Crear
	@Transactional
	public DetalleFactura createDetalleFactura(DetalleFacturaDto detalleFactura) {
		DetalleFactura detalleFacturaEntity = new DetalleFactura();
		detalleFacturaEntity.setCosto(detalleFactura.costo());
		detalleFacturaEntity.setDescripcionServicio(detalleFactura.descripcionServicio());
		detalleFacturaEntity.setFactura(detalleFactura.factura());
		return detalleFacturaRepository.save(detalleFacturaEntity);
	}

	// Eliminar
	public DetalleFactura deleteDetalleFactura(Long idDetalleFactura) {
		detalleFacturaRepository.deleteById(idDetalleFactura);
		return null;
	}

	// Modificar
	@Transactional
	public DetalleFactura updateDetalleFactura(Long idDetalleFactura, DetalleFacturaDto detalleFactura) {
		DetalleFactura detalleFacturaEntity = detalleFacturaRepository.findById(idDetalleFactura)
				.orElseThrow(() -> new NoSuchElementException("Factura no encontrada con el ID: " + idDetalleFactura));
		detalleFacturaEntity.setCosto(detalleFactura.costo());
		detalleFacturaEntity.setDescripcionServicio(detalleFactura.descripcionServicio());
		detalleFacturaEntity.setFactura(detalleFactura.factura());
		return detalleFacturaRepository.save(detalleFacturaEntity);
	}

}
