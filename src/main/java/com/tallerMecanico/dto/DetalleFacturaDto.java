package com.tallerMecanico.dto;

import com.tallerMecanico.entity.Factura;


public record DetalleFacturaDto(long idDetalleFactura, String descripcionServicio, double costo, Factura factura) {

}
