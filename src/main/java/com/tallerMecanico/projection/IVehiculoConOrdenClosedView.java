package com.tallerMecanico.projection;

import java.util.List;

public interface IVehiculoConOrdenClosedView {

	long getIdVehiculo();
	String getVin();
	String getMatricula();
	int getAnioModelo();
	String getColor();
	String getImagen();
	
	ITipoMotorClosedView getTipoMotor();
    IModeloClosedView getModelo();
    IClienteProjection getCliente();
    
    List<IOrdenServicioView> getOrdenServicio();

}
