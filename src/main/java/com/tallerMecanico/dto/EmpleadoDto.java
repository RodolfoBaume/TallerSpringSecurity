package com.tallerMecanico.dto;

import com.tallerMecanico.entity.Usuario;


public record EmpleadoDto(long idEmpleado, String nombre, String apellidoPaterno, String apellidoMaterno,  long nss, String curp, String RFC, String puesto, String observaciones,  Usuario usuario) {

}
