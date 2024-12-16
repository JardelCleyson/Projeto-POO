package com.dragenda.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.dragenda.model.Unidade;
import com.dragenda.util.DatabaseConnection;

public class UnidadeDAO {
    public void add(Unidade unidade) {
        String sql = "INSERT INTO unidades (nome, endereco, horario_abertura, horario_fechamento, dias_funcionamento) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, unidade.getNome());
            pstmt.setString(2, unidade.getEndereco());
            pstmt.setString(3, unidade.getHorarioAbertura().toString());
            pstmt.setString(4, unidade.getHorarioFechamento().toString());
            pstmt.setString(5, String.join(",", unidade.getDiasFuncionamento())); // Armazenando como string
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Unidade> getAll() {
        List<Unidade> unidades = new ArrayList<>();
        String sql = "SELECT * FROM unidades";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Unidade unidade = new Unidade(
                    rs.getString("nome"),
                    rs.getString("endereco"),
                    LocalTime.parse(rs.getString("horario_abertura")),
                    LocalTime.parse(rs.getString("horario_fechamento")),
                    List.of(rs.getString("dias_funcionamento").split(","))
                );
                unidades.add(unidade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return unidades;
    }

    // Métodos de update e delete podem ser implementados de forma semelhante
}




// package com.dragenda.dao;

// import java.sql.*;
// import java.util.ArrayList;
// import java.util.List;
// import java.time.LocalTime;

// import com.dragenda.model.Unidade;
// import com.dragenda.util.DatabaseConnection;


// public class UnidadeDAO {
//     public void createTable() {
//         String sql = "CREATE TABLE IF NOT EXISTS unidades (" +
//                      "id INTEGER PRIMARY KEY AUTOINCREMENT," +
//                      "nome TEXT NOT NULL," +
//                      "endereco TEXT NOT NULL," +
//                      "horario_abertura TEXT NOT NULL," +
//                      "horario_fechamento TEXT NOT NULL," +
//                      "dias_funcionamento TEXT NOT NULL" +
//                      ");";

//         try (Connection conn = DatabaseConnection.getConnection();
//              Statement stmt = conn.createStatement()) {
//             stmt.execute(sql);
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//     }

//     public void add(Unidade unidade) {
//         String sql = "INSERT INTO unidades (nome, endereco, horario_abertura, horario_fechamento, dias_funcionamento) VALUES (?, ?, ?, ?, ?)";

//         try (Connection conn = DatabaseConnection.getConnection();
//              PreparedStatement pstmt = conn.prepareStatement(sql)) {
//             pstmt.setString(1, unidade.getNome());
//             pstmt.setString(2, unidade.getEndereco());
//             pstmt.setString(3, unidade.getHorarioAbertura().toString());
//             pstmt.setString(4, unidade.getHorarioFechamento().toString());
//             pstmt.setString(5, String.join(",", unidade.getDiasFuncionamento())); // Armazenando como string
//             pstmt.executeUpdate();
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//     }

//     public List<Unidade> getAll() {
//         List<Unidade> unidades = new ArrayList<>();
//         String sql = "SELECT * FROM unidades";

//         try (Connection conn = DatabaseConnection.getConnection();
//              Statement stmt = conn.createStatement();
//              ResultSet rs = stmt.executeQuery(sql)) {

//             while (rs.next()) {
//                 Unidade unidade = new Unidade(
//                     rs.getString("nome"),
//                     rs.getString("endereco"),
//                     LocalTime.parse(rs.getString("horario_abertura")),
//                     LocalTime.parse(rs.getString("horario_fechamento")),
//                     List.of(rs.getString("dias_funcionamento").split(","))
//                 );
//                 unidades.add(unidade);
//             }
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//         return unidades;
//     }
    
//     // Métodos de update e delete podem ser implementados de forma semelhante
// }
