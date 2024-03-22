package com.tallerMecanico.dto;

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
public class RegistroUsuarioClienteDto {
	private UsuarioDto usuario;
    private ClienteDto cliente;
}
