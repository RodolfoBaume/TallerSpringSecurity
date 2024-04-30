package com.tallerMecanico.dto;

import com.tallerMecanico.entity.Cliente;

public class ActualizacionDto {

	private Cliente cliente;
    private String nuevoRol;

    // Constructor, getters y setters

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getNuevoRol() {
        return nuevoRol;
    }

    public void setNuevoRol(String nuevoRol) {
        this.nuevoRol = nuevoRol;
    }
}
