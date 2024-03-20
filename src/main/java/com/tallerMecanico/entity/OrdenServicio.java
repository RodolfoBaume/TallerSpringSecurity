package com.tallerMecanico.entity;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;

import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "ordenesServicios")
public class OrdenServicio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idOrdenServicio;
	private Date fechaOrden;
	private String falla;
	private String kilometraje;
	private String observaciones;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "estatusServicioId")
	private EstatusServicio estatusServicio;
	@OneToOne(mappedBy = "ordenServicio")
	private Factura factura;
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehiculoId")
    private Vehiculo vehiculo;
	@OneToMany(mappedBy = "ordenServicio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleOrdenServicio> detalleOrdenServicios = new ArrayList<>();
}
