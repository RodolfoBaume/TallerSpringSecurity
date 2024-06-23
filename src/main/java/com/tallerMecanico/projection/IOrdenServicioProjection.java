package com.tallerMecanico.projection;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface IOrdenServicioProjection {
	long getIdOrdenServicio();
    Date getFechaOrden();
    String getFalla();
    String getKilometraje();
    String getObservaciones();
    IEstatusServicioProjection getEstatusServicio(); 
    String getComentarios();
    IVehiculoSinOrden getVehiculo();

    /*
    @JsonProperty("estatusServicio")
    default String getEstatusServicioAsString() {
        IEstatusServicioProjection estatusServicio = getEstatusServicio();
        return estatusServicio != null ? estatusServicio.getEstatusServicio() : null;
    }
    */
}


