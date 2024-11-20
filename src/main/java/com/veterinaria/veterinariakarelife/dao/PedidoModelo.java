
package com.veterinaria.veterinariakarelife.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.veterinaria.veterinariakarelife.interfaces.ModeloInterface;
import com.veterinaria.veterinariakarelife.models.Cliente;
import com.veterinaria.veterinariakarelife.models.Estado;
import com.veterinaria.veterinariakarelife.models.Pedido;

public class PedidoModelo implements ModeloInterface<Pedido> {

    private final Connection connection;

    public PedidoModelo(Connection connection) {
        this.connection = connection;
    }
    
    @Override
    public List<Pedido> ejecutarSP(String accion, Pedido pedido) throws SQLException {
        String sql = "{call usp_Pedidos(?, ?, ?, ?, ?, ?)}";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, accion);
            switch (accion) {
                case "Insert":
                    stmt.setObject(2, null);
                    stmt.setInt(3, pedido.getCliente().getId());
                    stmt.setDate(4, java.sql.Date.valueOf(pedido.getFecha()));
                    stmt.setBigDecimal(5, pedido.getTotal());
                    stmt.setInt(6, pedido.getEstado().getId());
                    stmt.executeUpdate();
                    return null;
                case "Update": 
                    stmt.setInt(2, pedido.getId());
                    stmt.setInt(3, pedido.getCliente().getId());
                    stmt.setDate(4, java.sql.Date.valueOf(pedido.getFecha()));
                    stmt.setBigDecimal(5, pedido.getTotal());
                    stmt.setInt(6, pedido.getEstado().getId());
                    stmt.executeUpdate();
                    return null;
                case "Select":
                    ResultSet rs = stmt.executeQuery();
                    List<Pedido> pedidos = new ArrayList<>();
                    while (rs.next()) {
                        Cliente cliente = new Cliente(rs.getInt("cliente_id"), null, null, null, null, null, null);
                        Estado estado = new Estado(rs.getInt("estado_id"), null);
                        Pedido p = new Pedido(
                                rs.getInt("id"),
                                cliente,
                                rs.getDate("fecha_pedido").toLocalDate(),
                                rs.getBigDecimal("total"),
                                estado);
                        pedidos.add(p);
                    }
                    return pedidos;
                case "Status":
                    stmt.setInt(2, pedido.getId());
                    stmt.setInt(6, pedido.getEstado().getId());
                    stmt.executeUpdate();
                    return null;
                default:
                    return null;
            }
        }
    }
}
