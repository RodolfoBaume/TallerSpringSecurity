package com.tallerMecanico.projection;

import java.util.List;

public interface IVehiculoClienteClosedView {

	long getIdVehiculo();
    String getVin();
    String getMatricula();
    int getAnioModelo();
    String getColor();
    String getImagen();
    
    ITipoMotorClosedView getTipoMotor();
    IModeloClosedView getModelo();
    IClienteProjection getCliente();
    
    List<IOrdenServicioProjection2> getOrdenesServicios();
}
