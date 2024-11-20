
package com.veterinaria.veterinariakarelife.controller;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.veterinaria.veterinariakarelife.dao.FactoryModelo;
import com.veterinaria.veterinariakarelife.interfaces.Command;
import com.veterinaria.veterinariakarelife.interfaces.Observer;
import com.veterinaria.veterinariakarelife.models.Cita;
import com.veterinaria.veterinariakarelife.models.Estado;
import com.veterinaria.veterinariakarelife.services.EstadoConfirmado;
import com.veterinaria.veterinariakarelife.services.EstadoPendiente;
import com.veterinaria.veterinariakarelife.services.Sujeto;

public class ControladorCita implements Operacion<Cita>{
    private final FactoryModelo factoryModelo;

    private Sujeto sujeto = new Sujeto();

    public ControladorCita() throws SQLException{
        factoryModelo = new FactoryModelo();
    }

    public void ejecutarComando(Command comando) {
        comando.ejecutar();
    }

    public void agregarObservador(Observer observador) {
        sujeto.agregarObservador(observador);
    }

    @Override
    public void crear(Cita cita) {
        try {
            factoryModelo.getCitaModelo().ejecutarSP("Insert", cita);
            sujeto.notificarObservadores("Nueva cita creada para " + cita.getCliente().getNombre());
            cita.setState(new EstadoPendiente());
            cita.aplicarState();
        } catch (SQLException ex) {
            Logger.getLogger(ControladorCita.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actualizar(Cita cita) {
        try {
            factoryModelo.getCitaModelo().ejecutarSP("Update", cita);
            sujeto.notificarObservadores("Se actualizo la cita de " + cita.getCliente().getNombre());
            cita.setState(new EstadoConfirmado());
            cita.aplicarState();
        } catch (SQLException ex) {
            Logger.getLogger(ControladorCita.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public void cambiarEstado(int clienteId, Estado estado) {
        try {
            factoryModelo.getCitaModelo().ejecutarSP("Status", new Cita(clienteId, null, null, null, null, estado));
        } catch (SQLException ex) {
            Logger.getLogger(ControladorCita.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Cita> listar() {
        try {
            return factoryModelo.getCitaModelo().ejecutarSP("Select", null);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorCita.class.getName()).log(Level.SEVERE, null, ex);
            return Collections.emptyList(); // Return an empty list on error
        }
    }
    
}
