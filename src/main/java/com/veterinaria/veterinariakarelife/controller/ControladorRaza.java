
package com.veterinaria.veterinariakarelife.controller;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.veterinaria.veterinariakarelife.dao.FactoryModelo;
import com.veterinaria.veterinariakarelife.models.Estado;
import com.veterinaria.veterinariakarelife.models.Raza;

public class ControladorRaza implements Operacion<Raza> {
    private final FactoryModelo factoryModelo;

    public ControladorRaza() throws SQLException {
        factoryModelo = new FactoryModelo();
    }

    @Override
    public void crear(Raza raza) {
        try {
            factoryModelo.getRazaModelo().ejecutarSP("Insert", raza);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorRaza.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actualizar(Raza objeto) {
        try {
            factoryModelo.getRazaModelo().ejecutarSP("Update", objeto);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorRaza.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Raza> listar() {
        try {
            return factoryModelo.getRazaModelo().ejecutarSP("Select", null);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorRaza.class.getName()).log(Level.SEVERE, null, ex);
            return Collections.emptyList(); // Return an empty list on error
        }
    }

    @Override
    public void cambiarEstado(int razaId, Estado estado) {
        try {
            factoryModelo.getRazaModelo().ejecutarSP("Status", new Raza(razaId, null, null, estado));
        } catch (SQLException ex) {
            Logger.getLogger(ControladorRaza.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
