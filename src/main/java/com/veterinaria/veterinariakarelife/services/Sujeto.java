package com.veterinaria.veterinariakarelife.services;

import java.util.ArrayList;
import java.util.List;

import com.veterinaria.veterinariakarelife.interfaces.Observer;

public class Sujeto {
    private List<Observer> observadores = new ArrayList<>();

    public void agregarObservador(Observer observador) {
        observadores.add(observador);
    }

    public void eliminarObservador(Observer observador) {
        observadores.remove(observador);
    }

    public void notificarObservadores(String mensaje) {
        for (Observer observador : observadores) {
            observador.actualizar(mensaje);
        }
    }
}
