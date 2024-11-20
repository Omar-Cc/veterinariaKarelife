
package com.veterinaria.veterinariakarelife.controller;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.veterinaria.veterinariakarelife.dao.FactoryModelo;
import com.veterinaria.veterinariakarelife.models.Estado;
import com.veterinaria.veterinariakarelife.models.PedidoDetalle;

public class ControladorPedidoDetalle implements Operacion<PedidoDetalle>{
    private final FactoryModelo factoryModelo;

    public ControladorPedidoDetalle() throws SQLException {
        factoryModelo = new FactoryModelo();
    }

    @Override
    public void crear(PedidoDetalle pedidoDetalle) {
        try {
            factoryModelo.getPedidoDetalleModelo().ejecutarSP("Insert", pedidoDetalle); 
        } catch (SQLException ex) {
            Logger.getLogger(ControladorPedidoDetalle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actualizar(PedidoDetalle pedidoDetalle) {
        try {
            factoryModelo.getPedidoDetalleModelo().ejecutarSP("Update", pedidoDetalle);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorPedidoDetalle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<PedidoDetalle> listar() {
        try {
            return factoryModelo.getPedidoDetalleModelo().ejecutarSP("Select", null);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorPedidoDetalle.class.getName()).log(Level.SEVERE, null, ex);
            return Collections.emptyList(); // Return an empty list on error
        }
    }

    @Override
    public void cambiarEstado(int pedidoDetalleId, Estado estado) {
        try {
            factoryModelo.getPedidoDetalleModelo().ejecutarSP("Status", new PedidoDetalle(pedidoDetalleId, null, null, 0, null));
        } catch (SQLException ex) {
            Logger.getLogger(ControladorPedidoDetalle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
