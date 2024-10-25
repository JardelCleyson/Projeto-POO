package com.dragenda.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.dragenda.model.Medico;
import com.dragenda.model.Unidade;
import com.dragenda.util.DatabaseConnection;

public class MedicoDAO {
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS medicos (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                     "nome TEXT NOT NULL," +
                     "cpf TEXT NOT NULL," +
                     "crm TEXT NOT NULL," +
                     "especialidade TEXT NOT NULL," +
                     "unidade_id INTEGER," +
                     "FOREIGN KEY(unidade_id) REFERENCES unidades(id)" +
                     ");";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(Medico medico) {
        String sql = "INSERT INTO medicos (nome, cpf, crm, especialidade, unidade_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, medico.getNomeCompleto());
            pstmt.setString(2, medico.getCpf());
            pstmt.setString(3, medico.getCrm());
            pstmt.setString(4, medico.getEspecialidade());
            pstmt.setInt(5, medico.getUnidadeVinculada().getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Medico> getAll() {
        List<Medico> medicos = new ArrayList<>();
        String sql = "SELECT * FROM medicos";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Unidade unidade = findUnidadeById(rs.getInt("unidade_id")); // Busca a unidade pelo ID
                Medico medico = new Medico(
                    rs.getInt("id"), // Captura o ID do médico
                    rs.getString("nome"),
                    rs.getString("cpf"),
                    rs.getString("crm"),
                    rs.getString("especialidade"),
                    unidade
                );
                medicos.add(medico);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicos;
    }
    
    private Unidade findUnidadeById(int id) {
        Unidade unidade = null;
        String sql = "SELECT * FROM unidades WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    unidade = new Unidade(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("endereco"),
                        rs.getTime("horario_abertura").toLocalTime(),
                        rs.getTime("horario_fechamento").toLocalTime(),
                        List.of(rs.getString("dias_funcionamento").split(",")) // Ajuste conforme seu formato
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return unidade; // Retorna a unidade encontrada ou null
    }
}
