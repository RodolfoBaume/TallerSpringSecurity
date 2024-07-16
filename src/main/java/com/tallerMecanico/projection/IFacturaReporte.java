package com.tallerMecanico.projection;

import java.sql.Timestamp;

public interface IFacturaReporte {
	long getIdFactura();
    Timestamp getFechaFactura();
    double getMonto();
    String getClienteNombre();
    String getClienteApellidoPaterno();
    String getClienteApellidoMaterno();
    String getVehiculoVin();
    String getVehiculoMatricula();
    String getVehiculoModelo();
}
