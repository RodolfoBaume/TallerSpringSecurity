package com.tallerMecanico.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "vehiculos")
public class Vehiculo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idVehiculo;
	private String vin;
	private String matricula;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "modeloId")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Modelo modelo;
	private String anioModelo;
	private String color;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tipoMotorId")
	private TipoMotor tipoMotor;
	private String imagen;
	@ManyToOne
    @JoinColumn(name = "clienteId")
	//@JsonManagedReference
	@JsonBackReference
    private Cliente cliente;
	@OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehiculoId")
    private List<OrdenServicio> ordenServicio;
}
