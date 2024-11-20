
package com.veterinaria.veterinariakarelife.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.veterinaria.veterinariakarelife.interfaces.ModeloInterface;
import com.veterinaria.veterinariakarelife.models.Cita;
import com.veterinaria.veterinariakarelife.models.Cliente;
import com.veterinaria.veterinariakarelife.models.Estado;
import com.veterinaria.veterinariakarelife.models.Mascota;
import com.veterinaria.veterinariakarelife.models.Servicio;

public class CitaModelo implements ModeloInterface<Cita> {

    private final Connection connection;

    public CitaModelo(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Cita> ejecutarSP(String accion, Cita cita) throws SQLException {
        String sql = "{call usp_Citas(?, ?, ?, ?, ?, ?, ?)}";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, accion);
            switch (accion) {
                case "Insert":
                    stmt.setObject(2, null);
                    stmt.setDate(3, java.sql.Date.valueOf(cita.getFecha_cita()));
                    stmt.setInt(4, cita.getMascota().getId());
                    stmt.setInt(5, cita.getServicio().getId());
                    stmt.setInt(6, cita.getCliente().getId());
                    stmt.setObject(7, null);
                    stmt.executeUpdate();
                    return null;
                case "Update":
                    stmt.setInt(2, cita.getId());
                    stmt.setDate(3, java.sql.Date.valueOf(cita.getFecha_cita()));
                    stmt.setInt(4, cita.getMascota().getId());
                    stmt.setInt(5, cita.getServicio().getId());
                    stmt.setInt(6, cita.getCliente().getId());
                    stmt.setInt(7, cita.getEstado().getId());
                    stmt.executeUpdate();
                    return null;
                case "Select":
                    ResultSet rs = stmt.executeQuery();
                    List<Cita> citas = new ArrayList<>();
                    while (rs.next()) {
                        Mascota mascota = new Mascota(rs.getInt("mascota_id"), null, null, null, null, null, null);
                        Servicio servicio = new Servicio(rs.getInt("servicio_id"), null, null, null, null);
                        Cliente cliente = new Cliente(rs.getInt("cliente_id"), null, null, null, null, null, null);
                        Estado estado = new Estado(rs.getInt("estado_id"), null);
                        Cita c = new Cita(
                                rs.getInt("id"),
                                rs.getDate("fecha_cita").toLocalDate(),
                                mascota,
                                servicio,
                                cliente,
                                estado);
                        citas.add(c);
                    }
                    return citas;
                case "Status":
                    stmt.setInt(2, cita.getId());
                    stmt.setInt(7, cita.getEstado().getId());
                    stmt.executeUpdate();
                    return null;
                default:
                    return null;
            }
        }
    }
}
