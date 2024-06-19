package com.tallerMecanico.entity;

import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private long idUsuario;
	private String username;
	private String password;
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinTable(name = "usuarios_roles", joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id_usuario")
	, inverseJoinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "id_rol"))
	private List<Rol> rol = new ArrayList<>();
	
	public Usuario() {
		super();
	}

	public Usuario(long idUsuario, String username, String password, List<Rol> rol) {
		super();
		this.idUsuario = idUsuario;
		this.username = username;
		this.password = password;
		this.rol = rol;
	}

	public long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Rol> getRol() {
		return rol;
	}

	public void setRol(List<Rol> rol) {
		this.rol = rol;
	}

}
