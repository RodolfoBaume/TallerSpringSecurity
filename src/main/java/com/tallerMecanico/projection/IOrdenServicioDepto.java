package com.tallerMecanico.projection;

import java.util.Date;
/*
public interface IOrdenServicioDepto {
	Long getIdOrdenServicio();
    Date getFechaOrden();
    String getFalla();
    String getKilometraje();
    String getObservaciones();
    String getComentarios();
    //IEstatusServicioProjection getEstatusServicio();
    String getEstatusServicio();
    String getDepartamento();
    //String getVehiculo();
}
*/

public interface IOrdenServicioDepto {
    long getIdOrdenServicio();
    Date getFechaOrden();
    String getFalla();
    String getKilometraje();
    String getObservaciones();
    String getComentarios();
    IEstatusServicioProjection getEstatusServicio();
    String getDepartamento();
    //String getModelo();
    //String getMarca();
    IVehiculoModelo getVehiculo();
}
