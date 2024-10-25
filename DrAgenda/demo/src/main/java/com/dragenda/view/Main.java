package com.dragenda.view;

import com.dragenda.dao.AgendamentoDAO;
import com.dragenda.dao.MedicoDAO;
import com.dragenda.dao.PacienteDAO;
import com.dragenda.dao.UnidadeDAO;
import com.dragenda.model.Agendamento;
import com.dragenda.model.Medico;
import com.dragenda.model.Paciente;
import com.dragenda.model.Unidade;
import com.dragenda.util.DatabaseConnection;
import com.dragenda.util.DatabaseInitializer;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private static final Scanner scanner = new Scanner(System.in);
    private static final UnidadeDAO unidadeDAO = new UnidadeDAO();
    private static final MedicoDAO medicoDAO = new MedicoDAO();
    private static final PacienteDAO pacienteDAO = new PacienteDAO();
    private static final AgendamentoDAO agendamentoDAO = new AgendamentoDAO();

    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            DatabaseInitializer.initializeDatabase();
            logger.info("Database initialized successfully.");

            boolean continuar = true;

            while (continuar) {
                mostrarMenu(); // Método para mostrar o menu principal

                int opcao = obterOpcaoUsuario(); // Lê a opção escolhida

                switch (opcao) {
                    case 1:
                        cadastrarUnidade();
                        break;
                    case 2:
                        cadastrarMedico();
                        break;
                    case 3:
                        cadastrarPaciente();
                        break;
                    case 4:
                        agendarConsulta();
                        break;
                    case 5:
                        exibirUnidades();
                        break;
                    case 6:
                        exibirMedicos();
                        break;
                    case 7:
                        exibirPacientes();
                        break;
                    case 8:
                        exibirAgendamentos();
                        break;
                    case 9:
                        continuar = false;
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Database connection error", e);
        } finally {
            scanner.close();
            logger.info("Scanner closed.");
        }
    }

    // Exibe o menu de opções
    private static void mostrarMenu() {
        System.out.println("=== Sistema de Agendamentos ===");
        System.out.println("1. Cadastrar Unidade");
        System.out.println("2. Cadastrar Médico");
        System.out.println("3. Cadastrar Paciente");
        System.out.println("4. Agendar Consulta");
        System.out.println("5. Ver Unidades Cadastradas");
        System.out.println("6. Ver Médicos Cadastrados");
        System.out.println("7. Ver Pacientes Cadastrados");
        System.out.println("8. Ver Agendamentos Realizados");
        System.out.println("9. Sair");
        System.out.print("Escolha uma opção: ");
    }

    // Método para obter a opção do usuário e garantir que seja um número válido
    private static int obterOpcaoUsuario() {
        int opcao;
        while (true) {
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.print("Por favor, insira um número válido: ");
                logger.log(Level.WARNING, "Invalid input for option", e);
            }
        }
        return opcao;
    }

    // Método para cadastrar uma nova unidade
    private static void cadastrarUnidade() {
        try {
            System.out.print("Digite o nome da unidade: ");
            String nome = scanner.nextLine().trim();

            System.out.print("Digite o endereço da unidade: ");
            String endereco = scanner.nextLine().trim();

            LocalTime horarioAbertura = obterHorario("horário de abertura (HH:mm)");
            LocalTime horarioFechamento = obterHorario("horário de fechamento (HH:mm)");

            System.out.print("Digite os dias de funcionamento (separados por vírgula, ex: SEGUNDA,TERÇA): ");
            String[] dias = scanner.nextLine().split(",");
            List<String> diasFuncionamento = List.of(dias).stream().map(String::trim).toList();

            Unidade unidade = new Unidade(nome, endereco, horarioAbertura, horarioFechamento, diasFuncionamento);
            unidadeDAO.add(unidade);

            System.out.println("Unidade cadastrada com sucesso!");
            logger.info("Unidade cadastrada com sucesso: " + nome);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao cadastrar unidade", e);
        }
    }

    // Método auxiliar para obter horário com validação
    private static LocalTime obterHorario(String prompt) {
        LocalTime horario;
        while (true) {
            try {
                System.out.print("Digite o " + prompt + ": ");
                horario = LocalTime.parse(scanner.nextLine());
                break;
            } catch (Exception e) {
                System.out.println("Horário inválido! Tente novamente.");
                logger.log(Level.WARNING, "Invalid time input", e);
            }
        }
        return horario;
    }

    // Método para cadastrar um novo médico
    private static void cadastrarMedico() {
        try {
            System.out.print("Digite o nome do médico: ");
            String nome = scanner.nextLine().trim();

            System.out.print("Digite o CPF do médico: ");
            String cpf = scanner.nextLine().trim();

            System.out.print("Digite o CRM do médico: ");
            String crm = scanner.nextLine().trim();

            System.out.print("Digite a especialidade do médico: ");
            String especialidade = scanner.nextLine().trim();

            System.out.print("Escolha a unidade vinculada (Digite o ID da unidade): ");
            List<Unidade> unidades = unidadeDAO.getAll();
            exibirUnidades(unidades);
            Unidade unidadeVinculada = escolherUnidade(unidades);

            // Verifica se a unidade é nula, se sim, solicita novamente
            while (unidadeVinculada == null) {
                System.out.println("O médico deve ter uma unidade vinculada. Por favor, escolha uma unidade.");
                unidadeVinculada = escolherUnidade(unidades);
            }

            // Criação do objeto Medico com ID 0 para autoincremento no banco
            Medico medico = new Medico(0, nome, cpf, crm, especialidade, unidadeVinculada);
            medicoDAO.add(medico);

            System.out.println("Médico cadastrado com sucesso!");
            logger.info("Médico cadastrado com sucesso: " + nome);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao cadastrar médico", e);
        }
    }

    // Método auxiliar para exibir unidades
    private static void exibirUnidades(List<Unidade> unidades) {
        for (int i = 0; i < unidades.size(); i++) {
            System.out.println((i + 1) + ". " + unidades.get(i).getNome());
        }
    }

    // Método auxiliar para escolher uma unidade
    private static Unidade escolherUnidade(List<Unidade> unidades) {
        int unidadeEscolhida;
        while (true) {
            unidadeEscolhida = obterOpcaoUsuario();
            if (unidadeEscolhida > 0 && unidadeEscolhida <= unidades.size()) {
                return unidades.get(unidadeEscolhida - 1);
            } else if (unidadeEscolhida == 0) {
                return null; // Não vincular
            } else {
                System.out.println("Opção inválida! Tente novamente.");
                logger.warning("Invalid unit selection");
            }
        }
    }

    // Método para cadastrar um novo paciente
    private static void cadastrarPaciente() {
        try {
            System.out.print("Digite o nome do paciente: ");
            String nome = scanner.nextLine().trim();

            System.out.print("Digite o CPF do paciente: ");
            String cpf = scanner.nextLine().trim();

            System.out.print("Escolha a unidade de preferência (Digite o ID da unidade ou 0 para não vincular): ");
            List<Unidade> unidades = unidadeDAO.getAll();
            exibirUnidades(unidades);
            Unidade unidadePreferencia = escolherUnidade(unidades);

            // Cria o paciente, unidadePreferencia pode ser nula
            Paciente paciente = new Paciente(0, nome, cpf, unidadePreferencia);
            pacienteDAO.add(paciente);

            System.out.println("Paciente cadastrado com sucesso!");
            logger.info("Paciente cadastrado com sucesso: " + nome);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao cadastrar paciente", e);
        }
    }

    // Método para agendar uma nova consulta
    private static void agendarConsulta() {
        System.out.print("Escolha o paciente (Digite o ID ou 0 para cancelar): ");
        List<Paciente> pacientes = pacienteDAO.getAll();
        exibirPacientes(pacientes);
        int pacienteEscolhido = obterOpcaoUsuario();

        if (pacienteEscolhido == 0) {
            System.out.println("Agendamento cancelado.");
            return;
        }

        Paciente paciente = pacientes.get(pacienteEscolhido - 1);

        System.out.print("Escolha o médico (Digite o ID ou 0 para cancelar): ");
        List<Medico> medicos = medicoDAO.getAll();
        exibirMedicos(medicos);
        int medicoEscolhido = obterOpcaoUsuario();

        if (medicoEscolhido == 0) {
            System.out.println("Agendamento cancelado.");
            return;
        }

        Medico medico = medicos.get(medicoEscolhido - 1);

        // Verifica se o médico possui uma unidade vinculada
        if (medico.getUnidadeVinculada() == null) {
            System.out.println("O médico selecionado não está vinculado a nenhuma unidade.");
            return;
        }

        LocalDate dataConsulta = obterDataConsulta();
        LocalTime horaConsulta = obterHorario("hora da consulta (HH:mm)");

        System.out.print("Digite o tipo de consulta (ROTINA, CIRURGIA, RETORNO): ");
        Agendamento.TipoConsulta tipoConsulta;
        while (true) {
            try {
                tipoConsulta = Agendamento.TipoConsulta.valueOf(scanner.nextLine().toUpperCase());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Tipo de consulta inválido! Tente novamente.");
            }
        }

        Agendamento agendamento = tipoConsulta == Agendamento.TipoConsulta.CIRURGIA
                ? new Agendamento(0, medico.getUnidadeVinculada(), paciente, medico, dataConsulta, horaConsulta,
                        tipoConsulta, "Local Cirurgia")
                : new Agendamento(0, medico.getUnidadeVinculada(), paciente, medico, dataConsulta, horaConsulta,
                        tipoConsulta);

        agendamentoDAO.inserirAgendamento(agendamento);
        System.out.println("Consulta agendada com sucesso!");
    }

    // Método auxiliar para exibir pacientes
    private static void exibirPacientes(List<Paciente> pacientes) {
        for (int i = 0; i < pacientes.size(); i++) {
            System.out.println((i + 1) + ". " + pacientes.get(i).getNomeCompleto());
        }
    }

    // Método auxiliar para exibir médicos
    private static void exibirMedicos(List<Medico> medicos) {
        for (int i = 0; i < medicos.size(); i++) {
            System.out.println((i + 1) + ". " + medicos.get(i).getNomeCompleto());
        }
    }

    // Método auxiliar para obter a data da consulta com validação
    private static LocalDate obterDataConsulta() {
        LocalDate dataConsulta;
        while (true) {
            try {
                System.out.print("Digite a data da consulta (YYYY-MM-DD): ");
                dataConsulta = LocalDate.parse(scanner.nextLine());
                break;
            } catch (Exception e) {
                System.out.println("Data inválida! Tente novamente.");
                logger.log(Level.WARNING, "Invalid date input", e);
            }
        }
        return dataConsulta;
    }

    // Método para exibir todas as unidades cadastradas
    private static void exibirUnidades() {
        List<Unidade> unidades = unidadeDAO.getAll();
        if (unidades.isEmpty()) {
            System.out.println("Nenhuma unidade cadastrada.");
        } else {
            System.out.println("=== Unidades Cadastradas ===");
            for (Unidade unidade : unidades) {
                System.out.println(unidade);
            }
        }
    }

    // Método para exibir todos os médicos cadastrados
    private static void exibirMedicos() {
        List<Medico> medicos = medicoDAO.getAll();
        if (medicos.isEmpty()) {
            System.out.println("Nenhum médico cadastrado.");
        } else {
            System.out.println("=== Médicos Cadastrados ===");
            for (Medico medico : medicos) {
                System.out.println(medico);
            }
        }
    }

    // Método para exibir todos os pacientes cadastrados
    private static void exibirPacientes() {
        List<Paciente> pacientes = pacienteDAO.getAll();
        if (pacientes.isEmpty()) {
            System.out.println("Nenhum paciente cadastrado.");
        } else {
            System.out.println("=== Pacientes Cadastrados ===");
            for (Paciente paciente : pacientes) {
                System.out.println(paciente);
            }
        }
    }

    // Método para exibir todos os agendamentos realizados
    private static void exibirAgendamentos() {
        List<Agendamento> agendamentos = agendamentoDAO.buscarAgendamentos(); // Supondo que exista um método getAll() em AgendamentoDAO
        if (agendamentos.isEmpty()) {
            System.out.println("Nenhum agendamento realizado.");
        } else {
            System.out.println("=== Agendamentos Realizados ===");
            for (Agendamento agendamento : agendamentos) {
                System.out.println(agendamento);
            }
        }
    }
}
