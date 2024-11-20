package com.veterinaria.veterinariakarelife.services;

import com.veterinaria.veterinariakarelife.interfaces.State;
import com.veterinaria.veterinariakarelife.models.Cita;

public class EstadoPendiente implements State {
    @Override
    public void manejar(Cita cita) {
        System.out.println("Cita está en estado pendiente.");
    }
}
