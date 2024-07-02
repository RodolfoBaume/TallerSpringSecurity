package com.tallerMecanico.projection;

import com.tallerMecanico.entity.Empleado;

public class EmpleadoOrdenImpl implements IEmpleadoOrden {
	
	private Empleado empleado;
	
	public EmpleadoOrdenImpl(Empleado empleado) {
		this.empleado = empleado;
	}

	@Override
	public long getIdEmpleado() {
		return empleado.getIdEmpleado();
	}

	@Override
	public String getNombre() {
		return empleado.getNombre();
	}

	@Override
	public String getApellidoPaterno() {
		return empleado.getApellidoPaterno();
	}

	@Override
	public String getApellidoMaterno() {
		return empleado.getApellidoMaterno();
	}

}
