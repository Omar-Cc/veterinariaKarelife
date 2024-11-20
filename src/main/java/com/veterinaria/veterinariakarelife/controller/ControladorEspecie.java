
package com.veterinaria.veterinariakarelife.controller;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.veterinaria.veterinariakarelife.dao.FactoryModelo;
import com.veterinaria.veterinariakarelife.models.Especie;
import com.veterinaria.veterinariakarelife.models.Estado;

public class ControladorEspecie implements Operacion<Especie>{
    private final FactoryModelo factoryModelo;

    public ControladorEspecie() throws SQLException{
        factoryModelo = new FactoryModelo();
    }

    @Override
    public void crear(Especie especie) {
        try {
            factoryModelo.getEspecieModelo().ejecutarSP("Insert", especie);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorEspecie.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actualizar(Especie especie) {
        try {
            factoryModelo.getEspecieModelo().ejecutarSP("Update", especie);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorEspecie.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public void cambiarEstado(int clienteId, Estado estado) {
        try {
            factoryModelo.getEspecieModelo().ejecutarSP("Status", new Especie(clienteId, null, estado));
        } catch (SQLException ex) {
            Logger.getLogger(ControladorEspecie.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Especie> listar() {
        try {
            return factoryModelo.getEspecieModelo().ejecutarSP("Select", null);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorEspecie.class.getName()).log(Level.SEVERE, null, ex);
            return Collections.emptyList(); // Return an empty list on error
        }
    }
    
}
