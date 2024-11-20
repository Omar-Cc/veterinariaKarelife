
package com.veterinaria.veterinariakarelife.controller;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.veterinaria.veterinariakarelife.dao.FactoryModelo;
import com.veterinaria.veterinariakarelife.models.Cuidado;
import com.veterinaria.veterinariakarelife.models.Estado;

public class ControladorCuidado implements Operacion<Cuidado>{
    private final FactoryModelo factoryModelo;

    public ControladorCuidado() throws SQLException{
        factoryModelo = new FactoryModelo();
    }

    @Override
    public void crear(Cuidado cuidado) {
        try {
            factoryModelo.getCuidadoModelo().ejecutarSP("Insert", cuidado);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorCuidado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actualizar(Cuidado cuidado) {
        try {
            factoryModelo.getCuidadoModelo().ejecutarSP("Update", cuidado);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorCuidado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public void cambiarEstado(int clienteId , Estado estado) {
        try {
            factoryModelo.getCuidadoModelo().ejecutarSP("Status", new Cuidado(clienteId, null, null, null, null, estado));
        } catch (SQLException ex) {
            Logger.getLogger(ControladorCuidado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Cuidado> listar() {
        try {
            return factoryModelo.getCuidadoModelo().ejecutarSP("Select", null);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorCuidado.class.getName()).log(Level.SEVERE, null, ex);
            return Collections.emptyList(); // Return an empty list on error
        }
    }
    
}
