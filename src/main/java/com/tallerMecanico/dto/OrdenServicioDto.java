package com.tallerMecanico.dto;

import java.util.Date;

import com.tallerMecanico.entity.Factura;
import com.tallerMecanico.entity.StatusServicio;

public record OrdenServicioDto(long idOrdenServicio, Date fechaOrden, String Falla, String kilometraje, String observaciones, StatusServicio statusServicio, Factura factura) {

}
