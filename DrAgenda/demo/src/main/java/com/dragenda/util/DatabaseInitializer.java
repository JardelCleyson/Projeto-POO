package com.dragenda.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    // Método para inicializar o banco de dados e criar as tabelas necessárias
    public static void initializeDatabase() {
        String sqlUnidade = "CREATE TABLE IF NOT EXISTS unidades ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nome TEXT NOT NULL,"
                + "endereco TEXT NOT NULL,"
                + "horario_abertura TEXT NOT NULL,"
                + "horario_fechamento TEXT NOT NULL,"
                + "dias_funcionamento TEXT NOT NULL);";

        String sqlMedico = "CREATE TABLE IF NOT EXISTS medicos ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nome TEXT NOT NULL,"
                + "cpf TEXT NOT NULL UNIQUE,"
                + "crm TEXT NOT NULL UNIQUE,"
                + "especialidade TEXT NOT NULL,"
                + "unidade_id INTEGER,"
                + "FOREIGN KEY (unidade_id) REFERENCES unidades (id));";

        String sqlPaciente = "CREATE TABLE IF NOT EXISTS pacientes ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nome TEXT NOT NULL,"
                + "cpf TEXT NOT NULL UNIQUE,"
                + "unidade_id INTEGER,"
                + "FOREIGN KEY (unidade_id) REFERENCES unidades (id));";

        String sqlAgendamento = "CREATE TABLE IF NOT EXISTS agendamentos ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "unidade_id INTEGER,"
                + "paciente_id INTEGER,"
                + "medico_id INTEGER,"
                + "data DATE NOT NULL,"
                + "hora TIME NOT NULL,"
                + "tipo_consulta TEXT NOT NULL,"
                + "local_cirurgia TEXT,"
                + "FOREIGN KEY (unidade_id) REFERENCES unidades (id),"
                + "FOREIGN KEY (paciente_id) REFERENCES pacientes (id),"
                + "FOREIGN KEY (medico_id) REFERENCES medicos (id));";

        try (Connection connection = DatabaseConnection.getConnection(); 
             Statement statement = connection.createStatement()) {
            
            // Executa as instruções de criação das tabelas
            statement.execute(sqlUnidade);
            statement.execute(sqlMedico);
            statement.execute(sqlPaciente);
            statement.execute(sqlAgendamento);

            System.out.println("Tabelas criadas com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace(); // Trata exceções relacionadas ao banco de dados
        }
    }
}