package com.tallerMecanico.projection;

import java.util.Date;
import java.util.List;

public interface IOrdenServicioProjection {
	long getIdOrdenServicio();
    Date getFechaOrden();
    String getFalla();
    String getKilometraje();
    String getObservaciones();
    IEstatusServicioProjection getEstatusServicio(); 
    String getComentarios();
    IVehiculoSinOrden getVehiculo();
    List<IDetalleOrdenServicioProjection> getDetalleOrdenServicios();

    /*
    @JsonProperty("estatusServicio")
    default String getEstatusServicioAsString() {
        IEstatusServicioProjection estatusServicio = getEstatusServicio();
        return estatusServicio != null ? estatusServicio.getEstatusServicio() : null;
    }
    */
}


