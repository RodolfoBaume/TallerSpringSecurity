package com.tallerMecanico.controller;


import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tallerMecanico.dto.AuthRespuestaDto;
import com.tallerMecanico.dto.LoginDto;
import com.tallerMecanico.dto.RegistroDto;
import com.tallerMecanico.dto.RegistroResponseDto;
import com.tallerMecanico.dto.UsuarioActualDto;
import com.tallerMecanico.dto.UsuarioDto;
import com.tallerMecanico.service.UsuarioAuthService;


@RestController
//@CrossOrigin("*")
@RequestMapping("/api/auth")
public class RestAuthController {
	
	private UsuarioAuthService usuarioAuthService;
	
	@Autowired
    public RestAuthController(UsuarioAuthService usuarioService) {
    	this.usuarioAuthService = usuarioService;
    }

    //Método para poder registrar usuarios con role "user"
    @PostMapping("/register2")
    public ResponseEntity<RegistroResponseDto> registrarUsuario(@RequestBody UsuarioDto dtoRegistro,
            @RequestParam String role) {
    	return usuarioAuthService.registrarUsuario(dtoRegistro, role);
    }

    //Método para poder logear un usuario y obtener un token
    @PostMapping("/login")
    public ResponseEntity<AuthRespuestaDto> login(@RequestBody LoginDto dtoLogin) {
        ResponseEntity<AuthRespuestaDto> resp =null;
        try {
            resp = usuarioAuthService.login(dtoLogin);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return resp;
    }
    
    
    @GetMapping("/current-user")
    public ResponseEntity<UsuarioActualDto> usuarioActual(Principal principal) {
        return usuarioAuthService.obtenerUsuarioActual(principal);
    }
    
	
}