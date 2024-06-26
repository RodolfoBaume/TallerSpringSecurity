package com.tallerMecanico.projection;

import com.tallerMecanico.entity.EstatusServicio;

public class EstatusServicioProjectionImpl implements IEstatusServicioProjection {

	private EstatusServicio estatusServicio;

    public EstatusServicioProjectionImpl(EstatusServicio estatusServicio) {
        this.estatusServicio = estatusServicio;
    }

    @Override
    public long getIdEstatusServicio() {
        return estatusServicio.getIdEstatusServicio();
    }

    @Override
    public String getEstatusServicio() {
        return estatusServicio.getEstatusServicio();
    }

}
