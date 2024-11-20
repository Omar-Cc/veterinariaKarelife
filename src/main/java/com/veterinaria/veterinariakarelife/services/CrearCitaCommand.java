package com.veterinaria.veterinariakarelife.services;

import com.veterinaria.veterinariakarelife.controller.ControladorCita;
import com.veterinaria.veterinariakarelife.interfaces.Command;
import com.veterinaria.veterinariakarelife.models.Cita;

public class CrearCitaCommand implements Command {

    private ControladorCita controlador;
    private Cita cita;

    public CrearCitaCommand(ControladorCita controlador, Cita cita) {
        this.controlador = controlador;
        this.cita = cita;
    }

    @Override
    public void ejecutar() {
        controlador.crear(cita);
    }
}
