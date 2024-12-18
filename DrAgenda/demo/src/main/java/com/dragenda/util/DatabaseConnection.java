package com.dragenda.util;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Carrega as variáveis do .env
    private static final Dotenv dotenv = Dotenv.load();
    private static final String URL = dotenv.get("DB_URL");
    private static final String USER = dotenv.get("DB_USER");
    private static final String PASSWORD = dotenv.get("DB_PASSWORD");

    public static Connection getConnection() {
        try {
            // Carrega o driver JDBC (opcional para Java 8+)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Conecta ao banco de dados
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver MySQL não encontrado", e);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao obter a conexão com o banco de dados", e);
        }
    }
}
