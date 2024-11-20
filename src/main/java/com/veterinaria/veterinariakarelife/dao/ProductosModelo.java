
package com.veterinaria.veterinariakarelife.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.veterinaria.veterinariakarelife.interfaces.ModeloInterface;
import com.veterinaria.veterinariakarelife.models.Estado;
import com.veterinaria.veterinariakarelife.models.Producto;

public class ProductosModelo implements ModeloInterface<Producto> {
    private final Connection connection;

    public ProductosModelo(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Producto> ejecutarSP(String accion, Producto producto) throws SQLException {
        String sql = "{call usp_Productos(?, ?, ?, ?, ?, ?, ?)}";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, accion);
            switch (accion) {
                case "Insert":
                    stmt.setObject(2, null);
                    stmt.setString(3, producto.getNombre());
                    stmt.setString(4, producto.getDescripcion());
                    stmt.setBigDecimal(5, producto.getPrecio());
                    stmt.setInt(6, producto.getStock());
                    stmt.setInt(7, producto.getEstado().getId());
                    stmt.executeUpdate();
                    return null;
                case "Update":
                    stmt.setInt(2, producto.getId());
                    stmt.setString(3, producto.getNombre());
                    stmt.setString(4, producto.getDescripcion());
                    stmt.setBigDecimal(5, producto.getPrecio());
                    stmt.setInt(6, producto.getStock());
                    stmt.setInt(7, producto.getEstado().getId());
                    stmt.executeUpdate();
                    return null;
                case "Select":
                    ResultSet rs = stmt.executeQuery();
                    List<Producto> productos = new ArrayList<>();
                    while (rs.next()) {
                        Estado estado = new Estado(rs.getInt("estado_id"), null);
                        Producto p = new Producto(
                                rs.getInt("id"),
                                rs.getString("nombre"),
                                rs.getString("descripcion"),
                                rs.getBigDecimal("precio"),
                                rs.getInt("stock"),
                                estado);
                        productos.add(p);
                    }
                    return productos;
                case "Status":
                    stmt.setInt(2, producto.getId());
                    stmt.setInt(7, producto.getEstado().getId());
                    stmt.executeUpdate();
                    return null;
                default:
                    return null;
            }
        }
    }
}
