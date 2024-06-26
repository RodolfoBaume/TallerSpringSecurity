package com.tallerMecanico.projection;

import com.tallerMecanico.entity.Marca;

public class MarcaClosedViewImp implements IMarcaClosedView {
	
	private Marca marca;
	
	public MarcaClosedViewImp (Marca marca) {
		this.marca = marca;
	}

	@Override
	public long getIdMarca() {
		return marca.getIdMarca();
	}

	@Override
	public String getMarca() {
		return marca.getMarca();
	}

}
