package com.veterinaria.veterinariakarelife.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.veterinaria.veterinariakarelife.dao.FactoryModelo;
import com.veterinaria.veterinariakarelife.models.Estado;
import com.veterinaria.veterinariakarelife.models.Usuario;

public class ControladorUsuario implements Operacion<Usuario>{
    private final FactoryModelo factoryModelo;

    public ControladorUsuario() throws SQLException {
        factoryModelo = new FactoryModelo();
    }

    @Override
    public void crear(Usuario objeto) {
        try {
            factoryModelo.getUsuarioModelo().ejecutarSP("Insert", objeto);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actualizar(Usuario objeto) {
        try {
            factoryModelo.getUsuarioModelo().ejecutarSP("Update", objeto);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Usuario> listar() {
        /*
            try {
                return factoryModelo.getUsuarioModelo().ejecutarSP("Select", null);
            } catch (SQLException ex) {
                Logger.getLogger(ControladorServicio.class.getName()).log(Level.SEVERE, null, ex);
                return Collections.emptyList(); // Return an empty list on error
            }
        */
        return null;
    }

    @Override
    public void cambiarEstado(int clienteId, Estado estado) {
        /*
            try {
                factoryModelo.getUsuarioModelo().ejecutarSP("Status", new Usuario(clienteId, null, null, null, estado));
            } catch (SQLException ex) {
                Logger.getLogger(ControladorServicio.class.getName()).log(Level.SEVERE, null, ex);
            }
        */
    }

    public Usuario buscarPorId(int id) {
        try {
            return factoryModelo.getUsuarioModelo().ejecutarSP("SelectById", new Usuario(id, null, null));
        } catch (SQLException ex) {
            Logger.getLogger(ControladorServicio.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
