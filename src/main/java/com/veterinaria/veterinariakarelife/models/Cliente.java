
package com.veterinaria.veterinariakarelife.models;

import com.veterinaria.veterinariakarelife.interfaces.Observer;

public class Cliente implements Observer {
    private int id;
    private String nombre;
    private String email;
    private String telefono;
    private String direccion;
    private Estado estado;
    private Usuario usuario;

    public Cliente(int id, String nombre, String email, String telefono, String direccion, Usuario usuario, Estado estado) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.usuario = usuario;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    @Override
    public void actualizar(String mensaje) {
        System.out.println("Cliente " + nombre + " recibió notificación: " + mensaje);
    }
}
