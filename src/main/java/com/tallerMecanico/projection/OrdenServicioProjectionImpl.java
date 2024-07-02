package com.tallerMecanico.projection;

import java.util.Date;
import java.util.List;

import com.tallerMecanico.entity.OrdenServicio;

public class OrdenServicioProjectionImpl implements IOrdenServicioProjection{

	private OrdenServicio ordenServicio;
    private List<IDetalleOrdenServicioProjection> detalles;

    public OrdenServicioProjectionImpl(OrdenServicio ordenServicio, List<IDetalleOrdenServicioProjection> detalles) {
        this.ordenServicio = ordenServicio;
        this.detalles = detalles;
    }

    @Override
    public long getIdOrdenServicio() {
        return ordenServicio.getIdOrdenServicio();
    }

    @Override
    public Date getFechaOrden() {
        return ordenServicio.getFechaOrden();
    }

    @Override
    public String getFalla() {
        return ordenServicio.getFalla();
    }

    @Override
    public String getKilometraje() {
        return ordenServicio.getKilometraje();
    }

    @Override
    public String getObservaciones() {
        return ordenServicio.getObservaciones();
    }

    @Override
    public IEstatusServicioProjection getEstatusServicio() {
        return new EstatusServicioProjectionImpl(ordenServicio.getEstatusServicio());
    }

    @Override
    public String getComentarios() {
        return ordenServicio.getComentarios();
    }

    @Override
    public IVehiculoSinOrden getVehiculo() {
        return new VehiculoSinOrdenImpl(ordenServicio.getVehiculo());
    }

	@Override
	public List<IDetalleOrdenServicioProjection> getDetalleOrdenServicios() {
		return detalles;
	}

	@Override
	public IEmpleadoOrden getEmpleado() {
		return new EmpleadoOrdenImpl(ordenServicio.getEmpleado());
	}
}
