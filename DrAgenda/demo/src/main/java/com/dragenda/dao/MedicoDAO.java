package com.dragenda.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dragenda.model.Medico;
import com.dragenda.model.Unidade;
import com.dragenda.util.DatabaseConnection;

public class MedicoDAO { // Classe DAO para manipulação de médicos no banco de dados
    public void add(Medico medico) {
        String sql = "INSERT INTO Medico (nome, cpf, crm, especialidade, unidade_id) VALUES (?, ?, ?, ?, ?)";

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

    public List<Medico> getAll() { // Método para buscar todos os médicos
        List<Medico> medicos = new ArrayList<>();
        String sql = "SELECT * FROM Medico";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Unidade unidade = findUnidadeById(rs.getInt("unidade_id"));
                Medico medico = new Medico(
                    rs.getInt("id"),
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

    private Unidade findUnidadeById(int id) { // Método auxiliar para buscar uma unidade pelo ID
        Unidade unidade = null;
        String sql = "SELECT * FROM Unidade WHERE id = ?";

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
                        List.of(rs.getString("dias_funcionamento").split(","))
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return unidade;
    }
}
