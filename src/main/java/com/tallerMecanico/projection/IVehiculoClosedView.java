package com.tallerMecanico.projection;

import java.util.List;

public interface IVehiculoClosedView {

	long getIdVehiculo();
	String getVin();
	String getMatricula();
	int getAnioModelo();
	String getColor();
	String getImagen();
	
	ITipoMotorClosedView getTipoMotor();
	
	IModeloClosedView getModelo();
	
	List<IOrdenServicioView> getOrdenServicio();

}
