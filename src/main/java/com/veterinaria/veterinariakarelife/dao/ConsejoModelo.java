
package com.veterinaria.veterinariakarelife.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.veterinaria.veterinariakarelife.interfaces.ModeloInterface;
import com.veterinaria.veterinariakarelife.models.Consejo;
import com.veterinaria.veterinariakarelife.models.Especie;
import com.veterinaria.veterinariakarelife.models.Estado;
import com.veterinaria.veterinariakarelife.models.Raza;

public class ConsejoModelo implements ModeloInterface<Consejo> {
    private final Connection connection;

    public ConsejoModelo(Connection connection) {
        this.connection = connection;
    }
    
    @Override
    public List<Consejo> ejecutarSP(String accion, Consejo consejo) throws SQLException {
        String sql = "{call usp_Consejos(?, ?, ?, ?, ?)}";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, accion);
            switch (accion) {
                case "Insert":
                    stmt.setObject(2, null);
                    stmt.setString(3, consejo.getTitulo());
                    stmt.setString(4, consejo.getContenido());
                    stmt.setInt(5, consejo.getEstado().getId());
                    stmt.executeUpdate();
                    return null;
                case "Update":
                    stmt.setInt(2, consejo.getId());
                    stmt.setString(3, consejo.getTitulo());
                    stmt.setString(4, consejo.getContenido());
                    stmt.setInt(5, consejo.getEstado().getId());
                    stmt.executeUpdate();
                    return null;
                case "Select":
                    ResultSet rs = stmt.executeQuery();
                    List<Consejo> consejos = new ArrayList<>();
                    while (rs.next()) {
                        Especie especie = new Especie(rs.getInt("especie_id"), null, null);
                        Raza raza = new Raza(rs.getInt("raza_id"), null, especie, null);
                        Estado estado = new Estado(rs.getInt("estado_id"), null);
                        Consejo c = new Consejo(
                                rs.getInt("id"),
                                rs.getString("titulo"),
                                rs.getString("contenido"),
                                especie,
                                raza,
                                rs.getDate("fecha").toLocalDate(),
                                estado);
                        consejos.add(c);
                    }
                    return consejos;
                case "Status":
                    stmt.setInt(2, consejo.getId());
                    stmt.setInt(5, consejo.getEstado().getId());
                    stmt.executeUpdate();
                    return null;
                default:
                    return null;
            }
        }
    }
}
