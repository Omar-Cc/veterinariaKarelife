package com.veterinaria.veterinariakarelife.models;

import com.veterinaria.veterinariakarelife.interfaces.Observer;

public class Usuario implements Observer {
    private int id;
    private String email;
    private String password;

    public Usuario() {
    }

    public Usuario(int id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public Usuario(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void actualizar(String mensaje) {
        System.out.println("Cliente " + email + " recibió notificación: " + mensaje);
    }
}
