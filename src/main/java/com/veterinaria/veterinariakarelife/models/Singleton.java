package com.veterinaria.veterinariakarelife.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.*;

public class Singleton {

    private static Singleton instance;
    private Connection connection;

    private static final String URL = "jdbc:mysql://localhost:3308/clinicaveterinaria?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private Singleton() throws SQLException {
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static Singleton getInstance() {
        if (instance == null) {
            try {
                instance = new Singleton();
            } catch (SQLException e) {
                throw new RuntimeException("Error al obtener la conexión a la base de datos", e);
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    // Si la conexión está abierta, la cerramos
                    System.out.println("Cerrando conexión");
                    connection.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(Singleton.class.getName()).log(Level.SEVERE, "Error al cerrar la conexión", e);
            } finally {
                // Asegúrate de que la conexión se establece a null, incluso si ocurre un error
                connection = null;
            }
        }
    }
}
