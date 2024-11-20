
package com.veterinaria.veterinariakarelife.controller;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.veterinaria.veterinariakarelife.dao.FactoryModelo;
import com.veterinaria.veterinariakarelife.models.Estado;
import com.veterinaria.veterinariakarelife.models.Servicio;

public class ControladorServicio implements Operacion<Servicio>{
    private final FactoryModelo factoryModelo;

    public ControladorServicio() throws SQLException {
        factoryModelo = new FactoryModelo();
    }

    @Override
    public void crear(Servicio objeto) {
        try {
            factoryModelo.getServiciosModelo().ejecutarSP("Insert", objeto);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actualizar(Servicio objeto) {
        try {
            factoryModelo.getServiciosModelo().ejecutarSP("Update", objeto);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Servicio> listar() {
        try {
            return factoryModelo.getServiciosModelo().ejecutarSP("Select", null);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorServicio.class.getName()).log(Level.SEVERE, null, ex);
            return Collections.emptyList(); // Return an empty list on error
        }
    }

    @Override
    public void cambiarEstado(int clienteId, Estado estado) {
        try {
            factoryModelo.getServiciosModelo().ejecutarSP("Status", new Servicio(clienteId, null, null, null, estado));
        } catch (SQLException ex) {
            Logger.getLogger(ControladorServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
