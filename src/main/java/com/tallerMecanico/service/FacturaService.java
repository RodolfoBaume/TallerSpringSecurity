package com.tallerMecanico.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tallerMecanico.dto.FacturaDto;
import com.tallerMecanico.entity.Factura;
import com.tallerMecanico.projection.IFacturaProjection;
import com.tallerMecanico.repository.IFacturaRepository;

@Service
public class FacturaService implements IFacturaService {

	@Autowired
	private IFacturaRepository facturaRepository;

	@Override
	public List<IFacturaProjection> findBy() {
		return facturaRepository.findBy();
	}


	// consulta todos para paginación
	@Transactional(readOnly = true)
	public Page<IFacturaProjection> findPage(Pageable pageable) {
		return facturaRepository.findAllFacturas(pageable);
	}

	// consulta por id
	@Transactional(readOnly = true)
	public Factura findById(Long idFactura) {
		return facturaRepository.findById(idFactura).orElse(null);
	}
	
	
	@Transactional(readOnly = true)
	public IFacturaProjection findFacturaById(Long idFactura) {
		return facturaRepository.findFacturaById(idFactura);
	}
	
	
	
	
	
	

	// Crear
	@Transactional
	public Factura createFactura(FacturaDto factura) {
		Factura facturaEntity = new Factura();
		facturaEntity.setFechaFactura(factura.fechaFactura());
		facturaEntity.setMonto(factura.monto());
		facturaEntity.setOrdenServicio(factura.ordenServicio());
		System.out.println(factura.toString());
		return facturaRepository.save(facturaEntity);
	}

	// Eliminar
	public Factura deleteFactura(Long idFactura) {
		facturaRepository.deleteById(idFactura);
		return null;
	}

	// Modificar
	@Transactional
	public Factura updateFactura(Long idFactura, FacturaDto factura) {
		Factura facturaEntity = facturaRepository.findById(idFactura)
				.orElseThrow(() -> new NoSuchElementException("Factura no encontrada con el ID: " + idFactura));
		facturaEntity.setFechaFactura(factura.fechaFactura());
		facturaEntity.setMonto(factura.monto());
		facturaEntity.setOrdenServicio(factura.ordenServicio());

		return facturaRepository.save(facturaEntity);
	}


	



}
