package com.tallerMecanico.projection;

import java.util.List;

public class ClienteClosedViewImpl implements IClienteClosedView {

	private long idCliente;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String domicilio;
    private String telefono;
    private IUsuarioClosedView usuario;
    private List<IVehiculoClosedView> vehiculos;

    // Implementación de los getters y setters

    @Override
    public long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(long idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    @Override
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    @Override
    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    @Override
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public IUsuarioClosedView getUsuario() {
		return usuario;
	}

	public void setUsuario(IUsuarioClosedView usuario) {
		this.usuario = usuario;
	}

	@Override
    public List<IVehiculoClosedView> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(List<IVehiculoClosedView> vehiculos) {
        this.vehiculos = vehiculos;
    }
   
}

