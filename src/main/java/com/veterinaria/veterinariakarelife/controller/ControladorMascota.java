
package com.veterinaria.veterinariakarelife.controller;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.veterinaria.veterinariakarelife.dao.FactoryModelo;
import com.veterinaria.veterinariakarelife.models.Estado;
import com.veterinaria.veterinariakarelife.models.Mascota;

public class ControladorMascota implements Operacion<Mascota>{
    private FactoryModelo factoryModelo;
    
    @Override
    public void crear(Mascota mascota){
        try {
            factoryModelo.getMascotaModelo().ejecutarSP("Insert", mascota);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorMascota.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actualizar(Mascota mascota){
        try {
            factoryModelo.getMascotaModelo().ejecutarSP("Update", mascota);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorMascota.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Mascota> listar() {
        try {
            return factoryModelo.getMascotaModelo().ejecutarSP("Select", null);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorMascota.class.getName()).log(Level.SEVERE, null, ex);
            return Collections.emptyList(); // Return an empty list on error
        }
    }

    @Override
    public void cambiarEstado(int id, Estado estado){
        try {
            factoryModelo.getMascotaModelo().ejecutarSP("Status", new Mascota(id, null, null, null, null, null, estado));
        } catch (SQLException ex) {
            Logger.getLogger(ControladorMascota.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
}
