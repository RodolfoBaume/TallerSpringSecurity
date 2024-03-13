package com.tallerMecanico.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "facturas")
public class Factura {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idFactura;
	private Date fechaFactura;
	private double monto;
	@OneToOne
	@JoinColumn(name="ordenServicio_id")
	private OrdenServicio ordenServicio;
	@OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleFactura> detalleFacturas = new ArrayList<>();
}
