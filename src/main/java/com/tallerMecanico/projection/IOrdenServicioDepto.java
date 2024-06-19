package com.tallerMecanico.projection;

import java.util.Date;

public interface IOrdenServicioDepto {
	Long getIdOrdenServicio();
    Date getFechaOrden();
    String getFalla();
    String getKilometraje();
    String getObservaciones();
    String getComentarios();
    String getEstatusServicio();
    String getDepartamento();
    //String getVehiculo();
}
