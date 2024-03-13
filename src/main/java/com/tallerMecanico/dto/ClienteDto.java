package com.tallerMecanico.dto;

import java.util.List;

import com.tallerMecanico.entity.Usuario;
import com.tallerMecanico.entity.Vehiculo;


public record ClienteDto(long idCliente, String nombre, String apellidoPaterno, String apellidoMaterno, 
String domicilio, String telefono, Usuario usuario, List<Vehiculo> vehiculos) {

}
