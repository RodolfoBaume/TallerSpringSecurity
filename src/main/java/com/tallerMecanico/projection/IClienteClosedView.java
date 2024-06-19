package com.tallerMecanico.projection;

import java.util.List;

public interface IClienteClosedView {

	long getIdCliente();
	String getNombre();
	String getApellidoPaterno();
	String getApellidoMaterno();
	String getDomicilio();
	String getTelefono();
	
	List<IVehiculoClosedView> getVehiculos();
}
