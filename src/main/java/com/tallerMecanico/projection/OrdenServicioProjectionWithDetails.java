package com.tallerMecanico.projection;

import java.util.Date;
import java.util.List;

public class OrdenServicioProjectionWithDetails implements IOrdenServicioProjection {
    private final IOrdenServicioProjection ordenServicio;
    private List<IDetalleOrdenServicioProjection> detalleOrdenServicios;

    public OrdenServicioProjectionWithDetails(IOrdenServicioProjection ordenServicio) {
        this.ordenServicio = ordenServicio;
    }

    // Getters delegados
    public long getIdOrdenServicio() {
        return ordenServicio.getIdOrdenServicio();
    }

    public Date getFechaOrden() {
        return ordenServicio.getFechaOrden();
    }

    public String getFalla() {
        return ordenServicio.getFalla();
    }

    public String getKilometraje() {
        return ordenServicio.getKilometraje();
    }

    public String getObservaciones() {
        return ordenServicio.getObservaciones();
    }

    public IEstatusServicioProjection getEstatusServicio() {
        return ordenServicio.getEstatusServicio();
    }

    public String getComentarios() {
        return ordenServicio.getComentarios();
    }

    public IVehiculoSinOrden getVehiculo() {
        return ordenServicio.getVehiculo();
    }

    public IEmpleadoOrden getEmpleado() {
        return ordenServicio.getEmpleado();
    }

    public IFacturaProjection getFactura() {
        return ordenServicio.getFactura();
    }

    public List<IDetalleOrdenServicioProjection> getDetalleOrdenServicios() {
        return detalleOrdenServicios;
    }

    public void setDetalleOrdenServicios(List<IDetalleOrdenServicioProjection> detalleOrdenServicios) {
        this.detalleOrdenServicios = detalleOrdenServicios;
    }
}