package com.veterinaria.veterinariakarelife.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.veterinaria.veterinariakarelife.interfaces.ModeloInterface;
import com.veterinaria.veterinariakarelife.models.Cliente;
import com.veterinaria.veterinariakarelife.models.Estado;
import com.veterinaria.veterinariakarelife.models.Usuario;

public class ClienteModelo implements ModeloInterface<Cliente> {
    private final Connection connection;

    public ClienteModelo(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Cliente> ejecutarSP(String accion, Cliente cliente) throws SQLException {
        String sql = "{call usp_Clientes(?, ?, ?, ?, ?, ?, ?, ?)}";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, accion);
            switch (accion) {
                case "Insert":
                    stmt.setObject(2, null);
                    stmt.setString(3, cliente.getNombre());
                    stmt.setString(4, cliente.getEmail());
                    stmt.setString(5, cliente.getTelefono());
                    stmt.setString(6, cliente.getDireccion());
                    stmt.setInt(7, cliente.getUsuario().getId());
                    stmt.setInt(8, cliente.getEstado().getId());
                    stmt.executeUpdate();
                    return null; // o lanzar una excepción si es necesario
                case "Update":
                    stmt.setInt(2, cliente.getId());
                    stmt.setString(3, cliente.getNombre());
                    stmt.setString(4, cliente.getEmail());
                    stmt.setString(5, cliente.getTelefono());
                    stmt.setString(6, cliente.getDireccion());
                    stmt.setInt(7, cliente.getUsuario().getId());
                    stmt.setInt(8, cliente.getEstado().getId());
                    stmt.executeUpdate();
                    return null; // o lanzar una excepción si es necesario
                case "Select":
                    stmt.setObject(2, null); // Puedes establecerlo como null o pasar un valor
                    stmt.setObject(3, null);
                    stmt.setObject(4, null);
                    stmt.setObject(5, null);
                    stmt.setObject(6, null);
                    stmt.setObject(7, null);
                    stmt.setObject(8, null);
                    ResultSet rs = stmt.executeQuery();
                    List<Cliente> clientes = new ArrayList<>();
                    while (rs.next()) {
                        Usuario usuario = new Usuario(rs.getInt("usuario_id"), null, null);
                        Estado estado = new Estado(rs.getInt("estado_id"), null);
                        Cliente c = new Cliente(
                                rs.getInt("cliente_id"),
                                rs.getString("nombre"),
                                rs.getString("email"),
                                rs.getString("telefono"),
                                rs.getString("direccion"),
                                usuario,
                                estado);
                        clientes.add(c);
                    }
                    return clientes;
                case "Status":
                    stmt.setInt(2, cliente.getId());
                    stmt.setInt(8, cliente.getEstado().getId());
                    stmt.executeUpdate();
                    return null;
                default:
                    return null;
            }
        }
    }

    public Cliente obtenerClienteByUsuarioId(int usuarioId) {
        String sql = "SELECT * FROM clinicaveterinaria.Clientes WHERE usuario_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Cliente(
                            rs.getInt("cliente_id"),
                            rs.getString("nombre"),
                            rs.getString("email"),
                            rs.getString("telefono"),
                            rs.getString("direccion"),
                            new Usuario(rs.getInt("usuario_id"), null, null),
                            new Estado(rs.getInt("estado_id"), null));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
