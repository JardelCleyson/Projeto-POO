package com.dragenda.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dragenda.model.Paciente;
import com.dragenda.model.Unidade;
import com.dragenda.util.DatabaseConnection;

public class PacienteDAO {
    public void add(Paciente paciente) {
        String sql = "INSERT INTO Paciente (nome, cpf, unidade_id) VALUES (?, ?, ?)";

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
        String sql = "SELECT * FROM Paciente";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Unidade unidade = findUnidadeById(rs.getInt("unidade_id"));
                Paciente paciente = new Paciente(
                    rs.getInt("id"),
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
