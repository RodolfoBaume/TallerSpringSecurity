package com.tallerMecanico.projection;

import java.util.Date;
import java.util.List;

public interface IFacturaProjection {
	long getIdFactura();
	Date getFechaFactura();
	double getMonto();

	//List<IDetalleFacturaProjection> getDetalleFactura();
	
}
