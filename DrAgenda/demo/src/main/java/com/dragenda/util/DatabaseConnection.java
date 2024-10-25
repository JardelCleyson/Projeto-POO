package com.dragenda.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:clinicas.db"; // Nome do arquivo do banco de dados

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
