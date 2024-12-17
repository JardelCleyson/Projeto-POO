package com.dragenda.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://host:port/nome_banco";
    private static final String USER = "usuario";
    private static final String PASSWORD = "senha";

    // Método para obter a conexão com o banco de dados
    public static Connection getConnection() {
        try {
            // Carregar o driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Estabelecer a conexão
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Erro ao obter a conexão com o banco de dados: " + e.getMessage(), e);
        }
    }
}




// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.SQLException;

// public class DatabaseConnection {
//     private static final String URL = "jdbc:sqlite:clinicas.db"; // Nome do arquivo do banco de dados

//     public static Connection getConnection() throws SQLException {
//         return DriverManager.getConnection(URL);
//     }
// }
