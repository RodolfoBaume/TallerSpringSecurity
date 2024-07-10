package com.tallerMecanico.projection;

import java.util.Date;

public interface IFacturaProjection {
	long getIdFactura();
	Date getFechaFactura();
	double getMonto();
	
	IOrdenServicioSinDetalle getOrdenServicio();

}
