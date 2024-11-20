
package com.veterinaria.veterinariakarelife.controller;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.veterinaria.veterinariakarelife.dao.FactoryModelo;
import com.veterinaria.veterinariakarelife.models.Estado;
import com.veterinaria.veterinariakarelife.models.Pedido;

public class ControladorPedido implements Operacion<Pedido>{
    private final FactoryModelo factoryModelo;

    public ControladorPedido() throws SQLException{
        factoryModelo = new FactoryModelo();
    }

    @Override
    public void crear(Pedido pedido) {
        try {
            factoryModelo.getPedidoModelo().ejecutarSP("Insert", pedido);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorPedido.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actualizar(Pedido pedido) {
        try {
            factoryModelo.getPedidoModelo().ejecutarSP("Update", pedido);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorPedido.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Pedido> listar() {
        try {
            return factoryModelo.getPedidoModelo().ejecutarSP("Select", null);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorPedido.class.getName()).log(Level.SEVERE, null, ex);
            return Collections.emptyList(); // Return an empty list on error
        }
    }

    @Override
    public void cambiarEstado(int clienteId, Estado estado) {
        try {
            factoryModelo.getPedidoModelo().ejecutarSP("Status", new Pedido(clienteId, null, null, null, estado));
        } catch (SQLException ex) {
            Logger.getLogger(ControladorPedido.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
