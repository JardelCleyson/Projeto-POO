package com.dragenda.model;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.dragenda.dao.AgendamentoDAO;
import com.dragenda.dao.MedicoDAO;
import com.dragenda.dao.PacienteDAO;
import com.dragenda.dao.UnidadeDAO;
import com.dragenda.util.DatabaseConnection;

class MainTest {

    private static UnidadeDAO unidadeDAO;
    private static MedicoDAO medicoDAO;
    private static PacienteDAO pacienteDAO;
    private static AgendamentoDAO agendamentoDAO;

    @BeforeAll
    static void setUp() throws SQLException {
        // Configura a conexão com o banco de dados e inicializa os DAOs
        DatabaseConnection.getConnection();
        unidadeDAO = new UnidadeDAO();
        medicoDAO = new MedicoDAO();
        pacienteDAO = new PacienteDAO();
        agendamentoDAO = new AgendamentoDAO();
    }

    @Test
    void testCadastrarUnidade() {
        // Testa o cadastro de uma unidade
        String nome = "Unidade Teste";
        String endereco = "Endereço Teste";
        LocalTime horarioAbertura = LocalTime.of(8, 0);
        LocalTime horarioFechamento = LocalTime.of(18, 0);
        List<String> diasFuncionamento = List.of("SEGUNDA", "TERÇA");

        Unidade unidade = new Unidade(nome, endereco, horarioAbertura, horarioFechamento, diasFuncionamento);
        unidadeDAO.add(unidade);

        // Verifica se a unidade foi cadastrada corretamente
        assertNotNull(unidadeDAO.getAll());
        assertEquals(1, unidadeDAO.getAll().size());
        assertEquals(nome, unidadeDAO.getAll().get(0).getNome());
    }

    @Test
    void testCadastrarMedico() {
        // Testa o cadastro de um médico
        Unidade unidade = new Unidade("Unidade A", "Endereço A", LocalTime.of(8, 0), LocalTime.of(18, 0), List.of());
        unidadeDAO.add(unidade);

        Medico medico = new Medico(0, "Dr. Teste", "12345678901", "CRM123", "Cardiologia", unidade);
        medicoDAO.add(medico);

        // Verifica se o médico foi cadastrado corretamente
        assertNotNull(medicoDAO.getAll());
        assertEquals(1, medicoDAO.getAll().size());
        assertEquals("Dr. Teste", medicoDAO.getAll().get(0).getNomeCompleto());
    }

    @Test
    void testCadastrarPaciente() {
        // Testa o cadastro de um paciente
        Unidade unidade = new Unidade("Unidade B", "Endereço B", LocalTime.of(8, 0), LocalTime.of(18, 0), List.of());
        unidadeDAO.add(unidade);

        Paciente paciente = new Paciente(0, "Paciente Teste", "12345678901", unidade);
        pacienteDAO.add(paciente);

        // Verifica se o paciente foi cadastrado corretamente
        assertNotNull(pacienteDAO.getAll());
        assertEquals(1, pacienteDAO.getAll().size());
        assertEquals("Paciente Teste", pacienteDAO.getAll().get(0).getNomeCompleto());
    }

    @Test
    void testAgendarConsulta() {
        // Testa o agendamento de uma consulta
        Unidade unidade = new Unidade("Unidade C", "Endereço C", LocalTime.of(8, 0), LocalTime.of(18, 0), List.of());
        unidadeDAO.add(unidade);

        Medico medico = new Medico(0, "Dr. Agendador", "12345678901", "CRM456", "Pediatria", unidade);
        medicoDAO.add(medico);

        Paciente paciente = new Paciente(0, "Paciente Agendado", "12345678901", unidade);
        pacienteDAO.add(paciente);

        LocalDate dataConsulta = LocalDate.now().plusDays(1);
        LocalTime horaConsulta = LocalTime.of(10, 0);
        Agendamento agendamento = new Agendamento(0, unidade, paciente, medico, dataConsulta, horaConsulta, Agendamento.TipoConsulta.ROTINA);
        agendamentoDAO.inserirAgendamento(agendamento);

        // Verifica se o agendamento foi realizado corretamente
        assertNotNull(agendamentoDAO.buscarAgendamentos());
        assertEquals(1, agendamentoDAO.buscarAgendamentos().size());
        assertEquals(paciente.getNomeCompleto(), agendamentoDAO.buscarAgendamentos().get(0).getPaciente().getNomeCompleto());
    }

    @Test
    void testExibirUnidades() {
        // Testa a exibição das unidades cadastradas
        List<Unidade> unidades = unidadeDAO.getAll();
        assertFalse(unidades.isEmpty(), "Deve haver unidades cadastradas.");
    }

    @Test
    void testExibirMedicos() {
        // Testa a exibição dos médicos cadastrados
        List<Medico> medicos = medicoDAO.getAll();
        assertFalse(medicos.isEmpty(), "Deve haver médicos cadastrados.");
    }

    @Test
    void testExibirPacientes() {
        // Testa a exibição dos pacientes cadastrados
        List<Paciente> pacientes = pacienteDAO.getAll();
        assertFalse(pacientes.isEmpty(), "Deve haver pacientes cadastrados.");
    }

    @Test
    void testExibirAgendamentos() {
        // Testa a exibição dos agendamentos realizados
        List<Agendamento> agendamentos = agendamentoDAO.buscarAgendamentos();
        assertFalse(agendamentos.isEmpty(), "Deve haver agendamentos realizados.");
    }
}
