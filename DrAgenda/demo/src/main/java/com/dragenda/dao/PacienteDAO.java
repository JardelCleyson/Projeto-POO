package com.dragenda.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.dragenda.model.Paciente;
import com.dragenda.model.Unidade;
import com.dragenda.util.DatabaseConnection;
import java.time.LocalTime;

public class PacienteDAO {
    public void add(Paciente paciente) {
        String sql = "INSERT INTO pacientes (nome, cpf, unidade_id) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, paciente.getNomeCompleto());
            pstmt.setString(2, paciente.getCpf());
            pstmt.setInt(3, paciente.getUnidadePreferencia().getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Paciente> getAll() {
        List<Paciente> pacientes = new ArrayList<>();
        String sql = "SELECT * FROM pacientes";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Unidade unidade = findUnidadeById(rs.getInt("unidade_id")); // Busca a unidade pelo ID
                Paciente paciente = new Paciente(
                    rs.getInt("id"), // Captura o ID do paciente
                    rs.getString("nome"),
                    rs.getString("cpf"),
                    unidade
                );
                pacientes.add(paciente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pacientes;
    }

    private Unidade findUnidadeById(int id) {
        Unidade unidade = null;
        String sql = "SELECT * FROM unidades WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                unidade = new Unidade(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("endereco"),
                    LocalTime.parse(rs.getString("horario_abertura")),
                    LocalTime.parse(rs.getString("horario_fechamento")),
                    List.of(rs.getString("dias_funcionamento").split(","))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return unidade; // Retorna a unidade ou null se não encontrada
    }
}






// package com.dragenda.dao;

// import java.sql.*;
// import java.util.ArrayList;
// import java.util.List;
// import com.dragenda.model.Paciente;
// import com.dragenda.model.Unidade;
// import com.dragenda.util.DatabaseConnection;
// import java.time.LocalTime;


// public class PacienteDAO {
//     public void createTable() {
//         String sql = "CREATE TABLE IF NOT EXISTS pacientes (" +
//                      "id INTEGER PRIMARY KEY AUTOINCREMENT," +
//                      "nome TEXT NOT NULL," +
//                      "cpf TEXT NOT NULL," +
//                      "unidade_id INTEGER," +
//                      "FOREIGN KEY(unidade_id) REFERENCES unidades(id)" +
//                      ");";

//         try (Connection conn = DatabaseConnection.getConnection();
//              Statement stmt = conn.createStatement()) {
//             stmt.execute(sql);
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//     }

//     public void add(Paciente paciente) {
//         String sql = "INSERT INTO pacientes (nome, cpf, unidade_id) VALUES (?, ?, ?)";

//         try (Connection conn = DatabaseConnection.getConnection();
//              PreparedStatement pstmt = conn.prepareStatement(sql)) {
//             pstmt.setString(1, paciente.getNomeCompleto());
//             pstmt.setString(2, paciente.getCpf());
//             pstmt.setInt(3, paciente.getUnidadePreferencia().getId());
//             pstmt.executeUpdate();
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//     }

//     public List<Paciente> getAll() {
//         List<Paciente> pacientes = new ArrayList<>();
//         String sql = "SELECT * FROM pacientes";

//         try (Connection conn = DatabaseConnection.getConnection();
//              Statement stmt = conn.createStatement();
//              ResultSet rs = stmt.executeQuery(sql)) {

//             while (rs.next()) {
//                 Unidade unidade = findUnidadeById(rs.getInt("unidade_id")); // Busca a unidade pelo ID
//                 Paciente paciente = new Paciente(
//                     rs.getInt("id"), // Captura o ID do paciente
//                     rs.getString("nome"),
//                     rs.getString("cpf"),
//                     unidade
//                 );
//                 pacientes.add(paciente);
//             }
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//         return pacientes;
//     }
    
//     private Unidade findUnidadeById(int id) {
//         Unidade unidade = null;
//         String sql = "SELECT * FROM unidades WHERE id = ?";

//         try (Connection conn = DatabaseConnection.getConnection();
//              PreparedStatement pstmt = conn.prepareStatement(sql)) {
//             pstmt.setInt(1, id);
//             ResultSet rs = pstmt.executeQuery();
//             if (rs.next()) {
//                 unidade = new Unidade(
//                     rs.getInt("id"),
//                     rs.getString("nome"),
//                     rs.getString("endereco"),
//                     LocalTime.parse(rs.getString("horario_abertura")),
//                     LocalTime.parse(rs.getString("horario_fechamento")),
//                     List.of(rs.getString("dias_funcionamento").split(","))
//                 );
//             }
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//         return unidade; // Retorna a unidade ou null se não encontrada
//     }
// }
