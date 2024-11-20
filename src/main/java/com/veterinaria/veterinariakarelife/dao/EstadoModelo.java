package com.veterinaria.veterinariakarelife.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.veterinaria.veterinariakarelife.interfaces.ModeloInterface;
import com.veterinaria.veterinariakarelife.models.Estado;

public class EstadoModelo implements ModeloInterface<Estado> {

    private final Connection connection;

    public EstadoModelo(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Estado> ejecutarSP(String accion, Estado estado) throws SQLException {
        String sql = "{call usp_Estados(?, ?, ?)}";
        ResultSet rs;
        List<Estado> estados;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, accion);

            switch (accion) {
                case "Update":
                    stmt.setInt(2, estado.getId());
                    stmt.setString(3, estado.getNombreEstado());
                    stmt.executeUpdate();
                    return null;
                case "Select":
                    stmt.setObject(2, null);
                    stmt.setObject(3, null);
                    rs = stmt.executeQuery();
                    estados = new ArrayList<>();
                    while (rs.next()) {
                        Estado e = new Estado(
                                rs.getInt("estado_id"),
                                rs.getString("nombre_estado"));
                        estados.add(e);
                    }
                    return estados;
                case "Status":
                    stmt.setInt(2, estado.getId());
                    stmt.setString(3, estado.getNombreEstado());
                    stmt.executeUpdate();
                    return null;
                case "SelectById":
                    stmt.setInt(2, estado.getId());
                    stmt.setObject(3, null);
                    rs = stmt.executeQuery();
                    estados = new ArrayList<>();
                    while (rs.next()) {
                        estado = new Estado(
                                rs.getInt("estado_id"),
                                rs.getString("nombre_estado"));
                        estados.add(estado);

                    }
                    return estados;
                default:
                    break;
            }
        }
        return null;
    }

}
