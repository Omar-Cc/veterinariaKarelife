
package com.veterinaria.veterinariakarelife.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.veterinaria.veterinariakarelife.interfaces.ModeloInterface;
import com.veterinaria.veterinariakarelife.models.Cuidado;
import com.veterinaria.veterinariakarelife.models.Especie;
import com.veterinaria.veterinariakarelife.models.Estado;
import com.veterinaria.veterinariakarelife.models.Raza;

public class CuidadoModelo implements ModeloInterface<Cuidado> {
    private final Connection connection;

    public CuidadoModelo(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Cuidado> ejecutarSP(String accion, Cuidado cuidado) throws SQLException {
        String sql = "{call usp_Cuidados(?, ?, ?, ?, ?, ?, ?)}";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, accion);
            switch (accion) {
                case "Insert":
                    stmt.setObject(2, null);
                    stmt.setInt(3, cuidado.getEspecie().getId());
                    stmt.setInt(4, cuidado.getRaza().getId());
                    stmt.setString(5, cuidado.getCuidados());
                    stmt.setString(6, cuidado.getRecomendaciones());
                    stmt.setInt(7, cuidado.getEstado().getId());
                    stmt.executeUpdate();
                    return null;
                case "Update":
                    stmt.setInt(2, cuidado.getId());
                    stmt.setInt(3, cuidado.getEspecie().getId());
                    stmt.setInt(4, cuidado.getRaza().getId());
                    stmt.setString(5, cuidado.getCuidados());
                    stmt.setString(6, cuidado.getRecomendaciones());
                    stmt.setInt(7, cuidado.getEstado().getId());
                    stmt.executeUpdate();
                    return null;
                case "Select":
                    ResultSet rs = stmt.executeQuery();
                    List<Cuidado> cuidados = new ArrayList<>();
                    while (rs.next()) {
                        Especie especie = new Especie(rs.getInt("especie_id"), null, null);
                        Raza raza = new Raza(rs.getInt("raza_id"), null, especie, null);
                        Estado estado = new Estado(rs.getInt("estado_id"), null);
                        Cuidado c = new Cuidado(
                                rs.getInt("id"),
                                especie,
                                raza,
                                rs.getString("consejos"),
                                rs.getString("recomendaciones_especificas"),
                                estado);
                        cuidados.add(c);
                    }
                    return cuidados;
                case "Status":
                    stmt.setInt(2, cuidado.getId());
                    stmt.setInt(7, cuidado.getEstado().getId());
                    stmt.executeUpdate();
                    return null;
                default:
                    return null;
            }
        }
    }
}
