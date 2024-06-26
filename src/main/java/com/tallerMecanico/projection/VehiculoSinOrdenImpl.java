package com.tallerMecanico.projection;

import com.tallerMecanico.entity.Vehiculo;

public class VehiculoSinOrdenImpl implements IVehiculoSinOrden {

	private Vehiculo vehiculo;

    public VehiculoSinOrdenImpl(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }
    
	@Override
	public long getIdVehiculo() {
		return vehiculo.getIdVehiculo();
	}

	@Override
	public String getVin() {
		return vehiculo.getVin();
	}

	@Override
	public String getMatricula() {
		return vehiculo.getMatricula();
	}

	@Override
	public int getAnioModelo() {
		return vehiculo.getAnioModelo();
	}

	@Override
	public String getColor() {
		return vehiculo.getColor();
	}

	@Override
	public String getImagen() {
		return vehiculo.getImagen();
	}

	@Override
	public ITipoMotorClosedView getTipoMotor() {
		return new TipoMotorClosedViewImpl(vehiculo.getTipoMotor());
	}

	@Override
	public IModeloClosedView getModelo() {
		return new ModeloClosedViewImpl(vehiculo.getModelo());
	}

	@Override
	public IClienteProjection getCliente() {
		return new ClienteProjectionImpl(vehiculo.getCliente());
	}

}
