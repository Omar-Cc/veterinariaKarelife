package com.veterinaria.veterinariakarelife.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.veterinaria.veterinariakarelife.models.Usuario;

public class UsuarioModelo {

    private final Connection connection;

    public UsuarioModelo(Connection connection) {
        this.connection = connection;
    }

    public Usuario ejecutarSP(String accion, Usuario usuario) throws SQLException {
        String sql = "{call usp_Usuarios(?, ?, ?, ?)}";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, accion);
            switch (accion) {
                case "Insert":
                    stmt.setObject(2, null);
                    stmt.setString(3, usuario.getEmail());
                    stmt.setString(4, usuario.getPassword());
                    stmt.executeUpdate();
                    return null;
                case "SelectByEmail":
                    stmt.setObject(2, null);
                    stmt.setObject(3, usuario.getEmail());
                    stmt.setObject(4, null);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            return new Usuario(
                                    rs.getInt("id"),
                                    rs.getString("email"),
                                    rs.getString("password"));
                        }
                    }
                    return null;
                default:
                    return null;
            }
        }
    }

    public Usuario findByEmail(String email) throws SQLException {
        System.out.println("Buscando usuario con email: " + email);
        String sql = "SELECT * FROM clinicaveterinaria.Usuarios WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("usuario_id"),
                            rs.getString("email"),
                            rs.getString("password"));
                }
            }
        }
        return null;
    }
}
