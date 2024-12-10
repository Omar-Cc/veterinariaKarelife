
package com.veterinaria.veterinariakarelife.controller;

import java.util.List;

import com.veterinaria.veterinariakarelife.models.Estado;

public interface Operacion<T> {
    void crear(T objeto);
    void actualizar(T objeto);
    List<T> listar();
    void cambiarEstado(int a, Estado b);
}

