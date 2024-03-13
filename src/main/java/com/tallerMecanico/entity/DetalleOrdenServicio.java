package com.tallerMecanico.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
@Table(name = "detalleOrdenServicios")
public class DetalleOrdenServicio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idOrdenServicios;
	@ManyToOne
	private OrdenServicio ordenServicio;
	private String descripcionServicio;
}
