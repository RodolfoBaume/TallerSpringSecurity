package com.tallerMecanico.dto;

import java.util.Date;

import com.tallerMecanico.entity.OrdenServicio;


public record FacturaDto(long idFactura, Date fechaFactura, double monto, OrdenServicio ordenServicio) {

}
