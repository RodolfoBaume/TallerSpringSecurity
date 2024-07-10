package com.tallerMecanico.service;

import com.tallerMecanico.dto.DetalleFacturaDto;
import com.tallerMecanico.entity.DetalleFactura;

public interface IDetalleFacturaService {

	DetalleFactura createDetalleFactura(DetalleFacturaDto detalleFactura);
	
	DetalleFactura deleteDetalleFactura(Long idDetalleFactura);
	
	DetalleFactura updateDetalleFactura(Long idDetalleFactura,	DetalleFacturaDto detalleFactura);
}
