package com.tallerMecanico.projection;

import java.util.Date;

public interface IOrdenServicioProjection {
	long getIdOrdenServicio();
    Date getFechaOrden();
    String getFalla();
    String getKilometraje();
    String getObservaciones();
    IEstatusServicioProjection getEstatusServicio(); 
    String getComentarios();
    IVehiculoSinOrden getVehiculo();
 }
