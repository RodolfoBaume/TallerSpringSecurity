package com.tallerMecanico.controller;

import java.security.Principal;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.tallerMecanico.dto.RegistroResponseDto;
import com.tallerMecanico.dto.UsuarioActualDto;
import com.tallerMecanico.dto.UsuarioDto;
import com.tallerMecanico.service.UsuarioAuthService;

@RestController
// @CrossOrigin("*")
@RequestMapping("/api/auth")
public class RestAuthController {

    private UsuarioAuthService usuarioAuthService;

    @Autowired
    public RestAuthController(UsuarioAuthService usuarioService) {
        this.usuarioAuthService = usuarioService;
    }

    // Método para poder registrar usuarios con role "user"
    @PostMapping("/register2")
    public ResponseEntity<?> registrarUsuario(@RequestBody UsuarioDto dtoRegistro, @RequestParam String role) {

        ResponseEntity<RegistroResponseDto> resp = null;
        String response = "";

        try {
            resp = usuarioAuthService.registrarUsuario(dtoRegistro, role);
        } catch (Exception e) {
            System.out.println(e.toString());
            response = "Credenciales Invalidas.";
            return new ResponseEntity<String>(response, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return resp;

    }

    // Método para poder logear un usuario y obtener un token
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto dtoLogin) {
        ResponseEntity<AuthRespuestaDto> token = null;
        String response = "";

        try {
            token = usuarioAuthService.login(dtoLogin);
        } catch (Exception e) {
            System.out.println(e.toString());
            response = "Credenciales Invalidas.";
            return new ResponseEntity<String>(response, HttpStatus.FORBIDDEN);
        }
        return token;

    }

    @GetMapping("/current-user")
    public ResponseEntity<?> usuarioActual(Principal principal) {
        ResponseEntity<UsuarioActualDto> currentUser = null;
        String response = "";

        try {
            currentUser = usuarioAuthService.obtenerUsuarioActual(principal);
        } catch (Exception e) {
            System.out.println(e.toString());
            response = "Imposible obtener el usuario actual";
            return new ResponseEntity<String>(response, HttpStatus.FORBIDDEN);
        }
        return currentUser;
    }

}