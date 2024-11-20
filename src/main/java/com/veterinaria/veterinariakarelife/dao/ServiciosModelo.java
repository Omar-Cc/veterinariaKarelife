
package com.veterinaria.veterinariakarelife.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.veterinaria.veterinariakarelife.interfaces.ModeloInterface;
import com.veterinaria.veterinariakarelife.models.Estado;
import com.veterinaria.veterinariakarelife.models.Servicio;

public class ServiciosModelo implements ModeloInterface<Servicio> {
    private final Connection connection;

    public ServiciosModelo(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Servicio> ejecutarSP(String accion, Servicio servicio) throws SQLException {
        String sql = "{call usp_Servicios(?, ?, ?, ?, ?, ?)}";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, accion);
            switch (accion) {
                case "Insert":
                    stmt.setObject(2, null);
                    stmt.setString(3, servicio.getNombre());
                    stmt.setString(4, servicio.getDescripcion());
                    stmt.setBigDecimal(5, servicio.getPrecio());
                    stmt.setInt(6, servicio.getEstado().getId());
                    stmt.executeUpdate();
                    return null;
                case "Update":
                    stmt.setInt(2, servicio.getId());
                    stmt.setString(3, servicio.getNombre());
                    stmt.setString(4, servicio.getDescripcion());
                    stmt.setBigDecimal(5, servicio.getPrecio());
                    stmt.setInt(6, servicio.getEstado().getId());
                    stmt.executeUpdate();
                    return null;
                case "Select":
                    stmt.setObject(2, null); 
                    stmt.setObject(3, null); 
                    stmt.setObject(4, null); 
                    stmt.setObject(5, null); 
                    stmt.setObject(6, null); 
                    ResultSet rs = stmt.executeQuery();
                    List<Servicio> servicios = new ArrayList<>();
                    while (rs.next()) {
                        Estado estado = new Estado(rs.getInt("estado_id"), rs.getString("nombre_estado"));
                        Servicio s = new Servicio(
                                rs.getInt("servicio_id"),
                                rs.getString("nombre"),
                                rs.getString("descripcion"),
                                rs.getBigDecimal("precio"),
                                estado);
                        servicios.add(s);
                    }
                    return servicios;
                case "Status":
                    stmt.setInt(2, servicio.getId());
                    stmt.setInt(6, servicio.getEstado().getId());
                    stmt.executeUpdate();
                    return null;
                default:
                    return null;
            }
        }
    }
}