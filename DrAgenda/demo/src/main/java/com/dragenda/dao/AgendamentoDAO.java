package com.dragenda.dao;

import com.dragenda.model.Agendamento;
import com.dragenda.model.Medico;
import com.dragenda.model.Paciente;
import com.dragenda.model.Unidade;
import com.dragenda.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoDAO {
    // Método para inserir um novo agendamento
    public void inserirAgendamento(Agendamento agendamento) {
        String sql = "INSERT INTO agendamentos (unidade_id, paciente_id, medico_id, data_consulta, hora_consulta, tipo_consulta, local_cirurgia) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            // Setando os valores do agendamento no PreparedStatement
            pstmt.setInt(1, agendamento.getUnidade().getId());
            pstmt.setInt(2, agendamento.getPaciente().getId());
            pstmt.setInt(3, agendamento.getMedico().getId());
            pstmt.setDate(4, Date.valueOf(agendamento.getDataConsulta())); // Convertendo LocalDate para Date
            pstmt.setTime(5, Time.valueOf(agendamento.getHoraConsulta())); // Convertendo LocalTime para Time
            pstmt.setString(6, agendamento.getTipoConsulta().name()); // Usando o nome do enum
            pstmt.setString(7, agendamento.getLocalCirurgia());

            // Executa a inserção
            int affectedRows = pstmt.executeUpdate();

            // Verifica se a inserção foi bem-sucedida
            if (affectedRows == 0) {
                throw new SQLException("Falha ao inserir agendamento, nenhuma linha afetada.");
            }

            // Obtém o ID gerado para o novo agendamento
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    agendamento.setId(generatedKeys.getInt(1)); // Atualiza o ID do agendamento
                } else {
                    throw new SQLException("Falha ao obter o ID do agendamento.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir agendamento: " + e.getMessage(), e);
        }
    }

    // Método para buscar todos os agendamentos
    public List<Agendamento> buscarAgendamentos() {
        String sql = "SELECT a.id, a.data_consulta, a.hora_consulta, a.tipo_consulta, a.local_cirurgia, "
                   + "u.id AS unidade_id, u.nome AS unidade_nome, "
                   + "p.id AS paciente_id, p.nomeCompleto AS paciente_nome, "
                   + "m.id AS medico_id, m.nomeCompleto AS medico_nome "
                   + "FROM agendamentos a "
                   + "JOIN unidades u ON a.unidade_id = u.id "
                   + "JOIN pacientes p ON a.paciente_id = p.id "
                   + "JOIN medicos m ON a.medico_id = m.id";

        List<Agendamento> agendamentos = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                // Criar objetos Unidade, Paciente e Médico a partir do ResultSet
                Unidade unidade = new Unidade(
                        rs.getInt("unidade_id"),
                        rs.getString("unidade_nome"),
                        "", // Endereço não está sendo buscado
                        null, // Horário de abertura não está sendo buscado
                        null, // Horário de fechamento não está sendo buscado
                        null // Dias de funcionamento não estão sendo buscados
                );
                Paciente paciente = new Paciente(
                        rs.getInt("paciente_id"),
                        rs.getString("paciente_nome"),
                        "", // CPF não está sendo buscado
                        unidade // A unidade preferida
                );
                Medico medico = new Medico(
                        rs.getInt("medico_id"),
                        rs.getString("medico_nome"),
                        "", // CPF não está sendo buscado
                        "", // CRM não está sendo buscado
                        "", // Especialidade não está sendo buscada
                        unidade // A unidade vinculada
                );

                // Criar o agendamento
                Agendamento agendamento = new Agendamento(
                        rs.getInt("id"),
                        unidade,
                        paciente,
                        medico,
                        rs.getDate("data_consulta").toLocalDate(),
                        rs.getTime("hora_consulta").toLocalTime(),
                        Agendamento.TipoConsulta.valueOf(rs.getString("tipo_consulta")),
                        rs.getString("local_cirurgia")
                );

                agendamentos.add(agendamento);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar agendamentos: " + e.getMessage(), e);
        }

        return agendamentos;
    }

    // Método para atualizar um agendamento
    public void atualizarAgendamento(Agendamento agendamento) {
        String sql = "UPDATE agendamentos SET unidade_id = ?, paciente_id = ?, medico_id = ?, data_consulta = ?, hora_consulta = ?, tipo_consulta = ?, local_cirurgia = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, agendamento.getUnidade().getId());
            pstmt.setInt(2, agendamento.getPaciente().getId());
            pstmt.setInt(3, agendamento.getMedico().getId());
            pstmt.setDate(4, Date.valueOf(agendamento.getDataConsulta()));
            pstmt.setTime(5, Time.valueOf(agendamento.getHoraConsulta()));
            pstmt.setString(6, agendamento.getTipoConsulta().name());
            pstmt.setString(7, agendamento.getLocalCirurgia());
            pstmt.setInt(8, agendamento.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar agendamento: " + e.getMessage(), e);
        }
    }

    // Método para deletar um agendamento pelo ID
    public void deletarAgendamento(int id) {
        String sql = "DELETE FROM agendamentos WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar agendamento: " + e.getMessage(), e);
        }
    }
}
