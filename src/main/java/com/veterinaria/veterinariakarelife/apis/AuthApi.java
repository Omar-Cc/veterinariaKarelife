package com.veterinaria.veterinariakarelife.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.veterinaria.veterinariakarelife.models.Usuario;
import com.veterinaria.veterinariakarelife.services.Auth;

@Controller
@RequestMapping("/api/auth")
public class AuthApi {

    @Autowired
    Auth auth;

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody Usuario entidad) {
        try {
            System.out.println("Recibido usuario: " + entidad.getEmail());
            Usuario _entidad = auth.login(entidad);
            if (_entidad != null) {
                System.out.println("Usuario encontrado: " + _entidad.getEmail());
                return new ResponseEntity<>(_entidad, HttpStatus.OK);
            } else {
                System.out.println("Usuario no encontrado o contraseña incorrecta");
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            System.out.println("Error en el servidor: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
