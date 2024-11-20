
package com.veterinaria.veterinariakarelife.controller;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.veterinaria.veterinariakarelife.dao.FactoryModelo;
import com.veterinaria.veterinariakarelife.models.Estado;
import com.veterinaria.veterinariakarelife.models.Producto;

public class ControladorProducto implements Operacion<Producto>{
    private final FactoryModelo factoryModelo;

    public ControladorProducto() throws SQLException{
        factoryModelo = new FactoryModelo();
    }

    @Override
    public void crear(Producto producto) {
        try {
            factoryModelo.getProductosModelo().ejecutarSP("Insert", producto);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actualizar(Producto producto) {
        try {
            factoryModelo.getProductosModelo().ejecutarSP("Update", producto);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Producto> listar() {
        try {
            return factoryModelo.getProductosModelo().ejecutarSP("Select", null);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorProducto.class.getName()).log(Level.SEVERE, null, ex);
            return Collections.emptyList(); // Return an empty list on error
        }
    }

    @Override
    public void cambiarEstado(int clienteId, Estado estado) {
        try {
            factoryModelo.getProductosModelo().ejecutarSP("Status", new Producto(clienteId, null, null, null, 0, estado));
        } catch (SQLException ex) {
            Logger.getLogger(ControladorProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
