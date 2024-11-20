package com.veterinaria.veterinariakarelife.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.veterinaria.veterinariakarelife.dao.FactoryModelo;
import com.veterinaria.veterinariakarelife.models.Usuario;

@Service
public class Auth {

    @Autowired
    FactoryModelo factory;

    public Usuario login(Usuario entidad) {
        try {
            System.out.println("Buscando usuario con email: " + entidad.getEmail());
            Usuario usuario = factory.getUsuarioModelo().findByEmail(entidad.getEmail());
            if (usuario != null) {
                System.out.println("Usuario encontrado: " + usuario.getEmail());
                if (usuario.getPassword().equals(entidad.getPassword())) {
                    System.out.println("Contraseña correcta");
                    return usuario;
                } else {
                    System.out.println("Contraseña incorrecta");
                }
            } else {
                System.out.println("Usuario no encontrado");
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error en la autenticación: " + e.getMessage());
            return null;
        }
    }

}
