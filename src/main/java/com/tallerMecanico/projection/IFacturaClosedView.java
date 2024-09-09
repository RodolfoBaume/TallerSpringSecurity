package com.tallerMecanico.projection;

import java.util.Date;
import java.util.List;

public interface IFacturaClosedView {
	long getIdFactura();
	Date getFechaFactura();
	double getMonto();	
	
	List<IDetalleFacturaProjection> getDetalleFactura();
	
	String getNombre();
    String getApellidoPaterno();
    String getApellidoMaterno();
    String getDomicilio();
    String getTelefono();
    
    //datos del vehiculo
    String getVin();
    String getMatricula();
    String getModelo();
    String getMarca();
    int getAnioModelo();
    String getColor();
    String getTipoMotor();
}
