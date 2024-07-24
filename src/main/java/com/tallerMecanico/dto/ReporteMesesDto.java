package com.tallerMecanico.dto;

import java.sql.Date;

public class ReporteMesesDto{
    Date fechaFactura; 
    Long monto;

	public ReporteMesesDto() {}

    public ReporteMesesDto(Date fechaFactura, Long monto) {
        this.fechaFactura = fechaFactura;
        this.monto = monto;
    }

    public Date getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(Date fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public Long getMonto() {
        return monto;
    }

    public void setMonto(Long monto) {
        this.monto = monto;
    }

    




}
