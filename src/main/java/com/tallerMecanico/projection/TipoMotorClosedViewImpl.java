package com.tallerMecanico.projection;

import com.tallerMecanico.entity.TipoMotor;

public class TipoMotorClosedViewImpl implements ITipoMotorClosedView {
	
	private TipoMotor tipoMotor;
	
	public TipoMotorClosedViewImpl(TipoMotor tipoMotor) {
		this.tipoMotor = tipoMotor;
	}

	@Override
	public long getIdTipoMotor() {
		return tipoMotor.getIdTipoMotor();
	}

	@Override
	public String getTipoMotor() {
		return tipoMotor.getTipoMotor();
	}

}
