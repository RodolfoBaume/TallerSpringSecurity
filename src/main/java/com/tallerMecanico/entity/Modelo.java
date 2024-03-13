package com.tallerMecanico.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name="modelos")
public class Modelo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idModelo;
	private String modelo;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "marcaId")
	private Marca marca;
}
