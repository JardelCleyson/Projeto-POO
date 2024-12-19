package com.dragenda.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dragenda.model.Unidade;
import com.dragenda.util.DatabaseConnection;

public class UnidadeDAO { // Classe DAO para manipulação de unidades no banco de dados
    public void add(Unidade unidade) {
        String sql = "INSERT INTO Unidade (nome, endereco, horario_abertura, horario_fechamento, dias_funcionamento) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, unidade.getNome());
            pstmt.setString(2, unidade.getEndereco());
            pstmt.setTime(3, java.sql.Time.valueOf(unidade.getHorarioAbertura())); // Converte LocalTime para Time
            pstmt.setTime(4, java.sql.Time.valueOf(unidade.getHorarioFechamento())); // Converte LocalTime para Time
            pstmt.setString(5, String.join(",", unidade.getDiasFuncionamento())); // Dias armazenados como string
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Unidade> getAll() { // Método para buscar todas as unidades
        List<Unidade> unidades = new ArrayList<>();
        String sql = "SELECT * FROM Unidade";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Unidade unidade = new Unidade(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("endereco"),
                    rs.getTime("horario_abertura").toLocalTime(),
                    rs.getTime("horario_fechamento").toLocalTime(),
                    List.of(rs.getString("dias_funcionamento").split(","))
                );
                unidades.add(unidade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return unidades;
    }
}
