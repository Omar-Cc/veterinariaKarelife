
package com.veterinaria.veterinariakarelife.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.veterinaria.veterinariakarelife.interfaces.ModeloInterface;
import com.veterinaria.veterinariakarelife.models.Adopcion;
import com.veterinaria.veterinariakarelife.models.Cliente;
import com.veterinaria.veterinariakarelife.models.Estado;
import com.veterinaria.veterinariakarelife.models.Mascota;

public class AdopcionModelo implements ModeloInterface<Adopcion> {
    private final Connection connection;

    public AdopcionModelo(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Adopcion> ejecutarSP(String accion, Adopcion adopcion) throws SQLException {
        String sql = "{call usp_Adopciones(?, ?, ?, ?, ?, ?)}";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, accion);
            switch (accion) {
                case "Insert":
                    stmt.setObject(2, null);
                    stmt.setInt(3, adopcion.getMascota().getId());
                    stmt.setDate(4, null);
                    stmt.setObject(5, null);
                    stmt.setObject(6, null);
                    stmt.executeUpdate();
                    return null;
                case "Update":
                    stmt.setInt(2, adopcion.getId());
                    stmt.setInt(3, adopcion.getMascota().getId());
                    stmt.setDate(4, java.sql.Date.valueOf(adopcion.getFecha_adopcion()));
                    stmt.setInt(5, adopcion.getCliente().getId());
                    stmt.setInt(6, adopcion.getEstado().getId());
                    stmt.executeUpdate();
                    return null;
                case "Select":
                    ResultSet rs = stmt.executeQuery();
                    List<Adopcion> adopciones = new ArrayList<>();
                    while (rs.next()) {
                        Cliente cliente = new Cliente(rs.getInt("cliente_id"), null, null, null, null, null, null);
                        Mascota mascota = new Mascota(rs.getInt("mascota_id"), null, null, null, null, null, null);
                        Estado estado = new Estado(rs.getInt("estado_id"), null);
                        Adopcion c = new Adopcion(
                                rs.getInt("id"),
                                cliente,
                                mascota,
                                estado,
                                rs.getDate("fecha_publicacion").toLocalDate(),
                                rs.getDate("fecha_adopcion").toLocalDate());
                        adopciones.add(c);
                    }
                    return adopciones;
                case "Status":
                    stmt.setInt(2, adopcion.getId());
                    stmt.setInt(5, adopcion.getEstado().getId());
                    stmt.executeUpdate();
                    return null;
                default:
                    return null;
            }
        }
    }
}
