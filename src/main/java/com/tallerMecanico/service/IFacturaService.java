package com.tallerMecanico.service;

import java.util.List;

import com.tallerMecanico.dto.FacturaDto;
import com.tallerMecanico.entity.Factura;
import com.tallerMecanico.projection.IFacturaProjection;

public interface IFacturaService {
	
	List<IFacturaProjection>findBy();
	
	Factura findById(Long idFactura);
	
	Factura createFactura(FacturaDto factura);
	
	Factura deleteFactura(Long idFactura);
	
	Factura updateFactura(Long idFactura, FacturaDto factura);
	
}
