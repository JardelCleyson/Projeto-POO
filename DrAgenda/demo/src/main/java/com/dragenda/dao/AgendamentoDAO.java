package com.dragenda.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import com.dragenda.model.Agendamento;
import com.dragenda.model.Medico;
import com.dragenda.model.Paciente;
import com.dragenda.model.Unidade;
import com.dragenda.util.DatabaseConnection;

public class AgendamentoDAO {
    // Método para inserir um novo agendamento
    public void inserirAgendamento(Agendamento agendamento) {
        String sql = "INSERT INTO Agendamentos (unidade_id, paciente_id, medico_id, data_consulta, hora_consulta, tipo_consulta, local_cirurgia) VALUES (?, ?, ?, ?, ?, ?, ?)";

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




    public List<Agendamento> buscarAgendamentos() {
    String sql = "SELECT Agendamentos.id AS agendamento_id, " +
                 "Unidade.id AS unidade_id, Unidade.nome AS unidade_nome, Unidade.endereco AS unidade_endereco, " +
                 "Unidade.horario_abertura, Unidade.horario_fechamento, Unidade.dias_funcionamento, " +
                 "Paciente.id AS paciente_id, Paciente.nome AS paciente_nome, Paciente.cpf AS paciente_cpf, " +
                 "Medico.id AS medico_id, Medico.nome AS medico_nome, Medico.cpf AS medico_cpf, " +
                 "Medico.crm AS medico_crm, Medico.especialidade AS medico_especialidade, " +
                 "Agendamentos.data_consulta, Agendamentos.hora_consulta, Agendamentos.tipo_consulta, " +
                 "Agendamentos.local_cirurgia " +
                 "FROM Agendamentos " +
                 "JOIN Unidade ON Agendamentos.unidade_id = Unidade.id " +
                 "JOIN Paciente ON Agendamentos.paciente_id = Paciente.id " +
                 "JOIN Medico ON Agendamentos.medico_id = Medico.id";

    List<Agendamento> agendamentos = new ArrayList<>();

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {

        while (rs.next()) {
            // Criar objeto Unidade
            Unidade unidade = new Unidade(
                    rs.getInt("unidade_id"),
                    rs.getString("unidade_nome"),
                    rs.getString("unidade_endereco"),
                    rs.getTime("horario_abertura").toLocalTime(),
                    rs.getTime("horario_fechamento").toLocalTime(),
                    List.of(rs.getString("dias_funcionamento").split(","))
            );

            // Criar objeto Paciente
            Paciente paciente = new Paciente(
                    rs.getInt("paciente_id"),
                    rs.getString("paciente_nome"),
                    rs.getString("paciente_cpf"),
                    unidade
            );

            // Criar objeto Médico
            Medico medico = new Medico(
                    rs.getInt("medico_id"),
                    rs.getString("medico_nome"),
                    rs.getString("medico_cpf"),
                    rs.getString("medico_crm"),
                    rs.getString("medico_especialidade"),
                    unidade
            );

            // Criar objeto Agendamento
            Agendamento agendamento = new Agendamento(
                    rs.getInt("agendamento_id"),
                    unidade,
                    paciente,
                    medico,
                    rs.getDate("data_consulta").toLocalDate(),
                    rs.getTime("hora_consulta").toLocalTime(),
                    Agendamento.TipoConsulta.valueOf(rs.getString("tipo_consulta").toUpperCase())
                    //rs.getString("tipo_consulta").equalsIgnoreCase("CIRURGIA") ? rs.getString("local_cirurgia") : null // Local de cirurgia apenas para CIRURGIA
            );

            agendamentos.add(agendamento); // Adicionar à lista
        }
    } catch (SQLException e) {
        throw new RuntimeException("Erro ao buscar agendamentos: " + e.getMessage(), e);
    }

    return agendamentos;
}  

    // Método para atualizar um agendamento
    public void atualizarAgendamento(Agendamento agendamento) {
        String sql = "UPDATE Agendamentos SET unidade_id = ?, paciente_id = ?, medico_id = ?, data_consulta = ?, hora_consulta = ?, tipo_consulta = ?, local_cirurgia = ? WHERE id = ?";

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
        String sql = "DELETE FROM Agendamentos WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar agendamento: " + e.getMessage(), e);
        }
    }
}
