
package com.veterinaria.veterinariakarelife.controller;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.veterinaria.veterinariakarelife.dao.FactoryModelo;
import com.veterinaria.veterinariakarelife.models.Adopcion;
import com.veterinaria.veterinariakarelife.models.Estado;

public class ControladorAdopcion implements Operacion<Adopcion>{
    private final FactoryModelo factoryModelo;

    public ControladorAdopcion() throws SQLException {
        factoryModelo = new FactoryModelo();
    }

    @Override
    public void crear(Adopcion objeto) {
        try {
            factoryModelo.getAdopcionModelo().ejecutarSP("Insert", objeto);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorAdopcion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actualizar(Adopcion objeto) {
        try {
            factoryModelo.getAdopcionModelo().ejecutarSP("Insert", objeto);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorAdopcion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public void cambiarEstado(int adopcionId, Estado estado) {
        try {
            factoryModelo.getAdopcionModelo().ejecutarSP("Status", new Adopcion(adopcionId, null, null, estado, null, null));
        } catch (SQLException ex) {
            Logger.getLogger(ControladorAdopcion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Adopcion> listar() {
        try {
            return factoryModelo.getAdopcionModelo().ejecutarSP("Select", null);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorAdopcion.class.getName()).log(Level.SEVERE, null, ex);
            return Collections.emptyList(); // Return an empty list on error
        }
    }
    
}
