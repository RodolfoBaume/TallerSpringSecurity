package com.tallerMecanico.dto;

import com.tallerMecanico.entity.OrdenServicio;


public record DetalleOrdenServicioDto(long idOrdenServicios, OrdenServicio ordenServicio, String descripcionServicio) {

}
