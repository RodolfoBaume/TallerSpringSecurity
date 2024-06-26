package com.tallerMecanico.projection;

import com.tallerMecanico.entity.Modelo;

public class ModeloClosedViewImpl implements IModeloClosedView {
	
	private Modelo modelo;
	
	public ModeloClosedViewImpl(Modelo modelo) {
		this.modelo = modelo;
	}

	@Override
	public long getIdModelo() {
		return modelo.getIdModelo();
	}

	@Override
	public String getModelo() {
		return modelo.getModelo();
	}

	@Override
	public IMarcaClosedView getMarca() {
		return new MarcaClosedViewImp(modelo.getMarca());
	}

}
