package com.tallerMecanico.projection;

import java.util.List;

public interface IClienteClosedView {

	long getIdCliente();
	String getNombre();
	String getApellidoPaterno();
	String getApellidoMaterno();
	String getDomicilio();
	String getTelefono();
	IUsuarioClosedView getUsuario();
	
	List<IVehiculoClosedView> getVehiculos();
}
