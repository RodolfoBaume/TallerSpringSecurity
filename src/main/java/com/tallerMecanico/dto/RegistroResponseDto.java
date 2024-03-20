package com.tallerMecanico.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class RegistroResponseDto {

	private String mensaje;
    private Long idUsuario;

    public RegistroResponseDto(String mensaje, Long idUsuario) {
        this.mensaje = mensaje;
        this.idUsuario = idUsuario;
    }
}
