package com.tallerMecanico.projection;

import java.util.Date;

public interface IOrdenServicioDepto {
    long getIdOrdenServicio();
    Date getFechaOrden();
    String getFalla();
    String getKilometraje();
    String getObservaciones();
    String getComentarios();
    IEstatusServicioProjection getEstatusServicio();
    String getDepartamento();
    IVehiculoModelo getVehiculo();
    IEmpleadoOrden getEmpleado();
    Long getIdFactura();
    Date getFechaFactura();
    //double getMonto();
}
