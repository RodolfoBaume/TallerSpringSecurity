package com.tallerMecanico.projection;


public interface IVehiculoReporte { 
    String getVin();
    String getMatricula();
    int getAnioModelo();
    String getColor();
    String getTipoMotor();
    String getModelo();
    String getCliente();
    long getNumeroServicio();
    Double getCostoTotal();
}
