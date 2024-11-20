
package com.veterinaria.veterinariakarelife.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.veterinaria.veterinariakarelife.interfaces.ModeloInterface;
import com.veterinaria.veterinariakarelife.models.Especie;
import com.veterinaria.veterinariakarelife.models.Estado;

public class EspecieModelo implements ModeloInterface<Especie> {
    private final Connection connection;

    public EspecieModelo(Connection connection) {
        this.connection = connection;
    }
    
    @Override
    public List<Especie> ejecutarSP(String accion, Especie especie) throws SQLException {
        String sql = "{call usp_Especies(?, ?, ?, ?)}";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, accion);
            switch (accion) {
                case "Insert":
                    stmt.setObject(2, null);
                    stmt.setString(3, especie.getNombre());
                    stmt.setInt(4, especie.getEstado().getId());
                    stmt.executeUpdate();
                    return null;
                case "Update":
                    stmt.setInt(2, especie.getId());
                    stmt.setString(3, especie.getNombre());
                    stmt.setInt(4, especie.getEstado().getId());
                    stmt.executeUpdate();
                    return null;
                case "Select":
                    ResultSet rs = stmt.executeQuery();
                    List<Especie> especies = new ArrayList<>();
                    while (rs.next()) {
                        Estado estado = new Estado(rs.getInt("estado_id"), null);
                        Especie e = new Especie(
                                rs.getInt("id"),
                                rs.getString("nombre_especie"),
                                estado);
                        especies.add(e);
                    }
                    return especies;
                case "Status":
                    stmt.setInt(2, especie.getId());
                    stmt.setInt(4, especie.getEstado().getId());
                    stmt.executeUpdate();
                    return null;
                default:
                    return null;
            }
        }
    }
}
