
package com.veterinaria.veterinariakarelife.controller;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.veterinaria.veterinariakarelife.dao.FactoryModelo;
import com.veterinaria.veterinariakarelife.models.Consejo;
import com.veterinaria.veterinariakarelife.models.Estado;

public class ControladorConsejo implements Operacion<Consejo>{
    private final FactoryModelo factoryModelo;

    public ControladorConsejo() throws SQLException{
        factoryModelo = new FactoryModelo();
    }

    @Override
    public void crear(Consejo consejo) {
        try {
            factoryModelo.getConsejoModelo().ejecutarSP("Insert", consejo);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorConsejo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actualizar(Consejo consejo) {
        try {
            factoryModelo.getConsejoModelo().ejecutarSP("Update", consejo);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorConsejo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public void cambiarEstado(int clienteId, Estado estado) {
        try {
            factoryModelo.getConsejoModelo().ejecutarSP("Status", new Consejo(clienteId, null, null, null, null, null, estado));
        } catch (SQLException ex) {
            Logger.getLogger(ControladorConsejo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Consejo> listar() {
        try {
            return factoryModelo.getConsejoModelo().ejecutarSP("Select", null);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorConsejo.class.getName()).log(Level.SEVERE, null, ex);
            return Collections.emptyList(); // Return an empty list on error
        }
    }
    
}
