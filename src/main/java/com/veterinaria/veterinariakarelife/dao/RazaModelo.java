
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
import com.veterinaria.veterinariakarelife.models.Raza;

public class RazaModelo implements ModeloInterface<Raza> {
    private final Connection connection;

    public RazaModelo(Connection connection) {
        this.connection = connection;
    }
    
    @Override
    public List<Raza> ejecutarSP(String accion, Raza raza) throws SQLException {
        String sql = "{call usp_Razas(?, ?, ?, ?, ?)}";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, accion);
            switch (accion) {
                case "Insert":
                    stmt.setObject(2, null);
                    stmt.setString(3, raza.getNombre());
                    stmt.setInt(4, raza.getEspecie().getId());
                    stmt.setInt(5, raza.getEstado().getId());
                    stmt.executeUpdate();
                    return null;
                case "Update":
                    stmt.setInt(2, raza.getId());
                    stmt.setString(3, raza.getNombre());
                    stmt.setInt(4, raza.getEspecie().getId());
                    stmt.setInt(5, raza.getEstado().getId());
                    stmt.executeUpdate();
                    return null;
                case "Select":
                    ResultSet rs = stmt.executeQuery();
                    List<Raza> razas = new ArrayList<>();
                    while (rs.next()) {
                        Especie especie = new Especie(rs.getInt("especie_id"), null, null);
                        Estado estado = new Estado(rs.getInt("estado_id"), null);
                        Raza r = new Raza(
                                rs.getInt("id"),
                                rs.getString("nombre_raza"),
                                especie,
                                estado);
                        razas.add(r);
                    }
                    return razas;
                case "Status":
                    stmt.setInt(2, raza.getId());
                    stmt.setInt(5, raza.getEstado().getId());
                    stmt.executeUpdate();
                    return null;
                default:
                    return null;
            }
        }
    }
}
