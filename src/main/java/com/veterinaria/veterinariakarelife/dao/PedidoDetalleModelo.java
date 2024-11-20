
package com.veterinaria.veterinariakarelife.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.veterinaria.veterinariakarelife.interfaces.ModeloInterface;
import com.veterinaria.veterinariakarelife.models.Pedido;
import com.veterinaria.veterinariakarelife.models.PedidoDetalle;
import com.veterinaria.veterinariakarelife.models.Producto;

public class PedidoDetalleModelo implements ModeloInterface<PedidoDetalle> {
    private final Connection connection;

    public PedidoDetalleModelo(Connection connection) {
        this.connection = connection;
    }
    
    @Override
    public List<PedidoDetalle> ejecutarSP(String accion, PedidoDetalle pedidoDetalle) throws SQLException {
        String sql = "{call usp_DetallesPedidos(?, ?, ?, ?, ?, ?)}";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, accion);
            switch (accion) {
                case "Insert":
                    stmt.setObject(2, null);
                    stmt.setInt(3, pedidoDetalle.getPedido().getId());
                    stmt.setInt(4, pedidoDetalle.getProducto().getId());
                    stmt.setInt(5, pedidoDetalle.getCantidad());
                    stmt.setBigDecimal(6, pedidoDetalle.getPrecio_unitario());
                    stmt.executeUpdate();
                    return null;
                case "Update":
                    stmt.setInt(2, pedidoDetalle.getId());
                    stmt.setInt(3, pedidoDetalle.getPedido().getId());
                    stmt.setInt(4, pedidoDetalle.getProducto().getId());
                    stmt.setInt(5, pedidoDetalle.getCantidad());
                    stmt.setBigDecimal(6, pedidoDetalle.getPrecio_unitario());
                    stmt.executeUpdate();
                    return null;
                case "Select":
                    ResultSet rs = stmt.executeQuery();
                    List<PedidoDetalle> detalles = new ArrayList<>();
                    while (rs.next()) {
                        Pedido pedido = new Pedido(rs.getInt("pedido_id"), null, null, null, null);
                        Producto producto = new Producto(rs.getInt("producto_id"), null, null, null, 0, null);
                        PedidoDetalle detalle = new PedidoDetalle(
                                rs.getInt("id"),
                                pedido,
                                producto,
                                rs.getInt("cantidad"),
                                rs.getBigDecimal("precio"));
                        detalles.add(detalle);
                    }
                    return detalles;
                case "Status":
                    stmt.setInt(2, pedidoDetalle.getId());
                    stmt.executeUpdate();
                    return null;
                default:
                    return null;
            }
        }
    }
}
