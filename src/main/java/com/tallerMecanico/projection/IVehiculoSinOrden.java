package com.tallerMecanico.projection;


public interface IVehiculoSinOrden {

	long getIdVehiculo();
	String getVin();
	String getMatricula();
	int getAnioModelo();
	String getColor();
	String getImagen();
	
	ITipoMotorClosedView getTipoMotor();
	
	IModeloClosedView getModelo();
	
	IClienteProjection getCliente();
	
}
