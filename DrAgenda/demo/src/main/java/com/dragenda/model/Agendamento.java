package com.dragenda.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Agendamento {
    private int id; // Identificador único do agendamento
    private Unidade unidade;
    private Paciente paciente;
    private Medico medico;
    private LocalDate dataConsulta;
    private LocalTime horaConsulta;
    private TipoConsulta tipoConsulta;
    private String localCirurgia; // Somente para cirurgias

    // Enum para os tipos de consulta
    public enum TipoConsulta {
       A, ROTINA, CIRURGIA, RETORNO
    }

    // Construtor principal
    public Agendamento(int id, Unidade unidade, Paciente paciente, Medico medico, LocalDate dataConsulta, LocalTime horaConsulta, TipoConsulta tipoConsulta) {
        this.id = id; // Atribuindo o ID
        this.unidade = unidade;
        this.paciente = paciente;
        this.medico = medico;
        this.dataConsulta = dataConsulta;
        this.horaConsulta = horaConsulta;
        this.tipoConsulta = tipoConsulta;

        validarDataHoraConsulta(); // Validação da data e hora
    }

    // Construtor específico para cirurgias
    public Agendamento(int id, Unidade unidade, Paciente paciente, Medico medico, LocalDate dataConsulta, LocalTime horaConsulta, TipoConsulta tipoConsulta, String localCirurgia) {
        this(id, unidade, paciente, medico, dataConsulta, horaConsulta, tipoConsulta);
        if (tipoConsulta == TipoConsulta.CIRURGIA) {
            this.localCirurgia = localCirurgia;
        } else {
            throw new IllegalArgumentException("Local de cirurgia só pode ser definido para consultas do tipo CIRURGIA.");
        }
    }

    // Valida se o horário da consulta está dentro do horário de funcionamento da unidade
    private void validarDataHoraConsulta() {
        // Validação do dia da semana
        String diaSemana = dataConsulta.getDayOfWeek().name().toUpperCase(); // Obtém o nome do dia da semana em maiúsculo
        if (unidade.getDiasFuncionamento().contains(diaSemana)) {// Verifica se o dia da semana está na lista de dias de funcionamento
            System.out.println("Dia da semana válido.");
        } else {
            throw new IllegalArgumentException("A unidade não funciona no dia " + diaSemana + ".");
        }

        // Validação do horário de funcionamento
        if (horaConsulta.isBefore(unidade.getHorarioAbertura()) || horaConsulta.isAfter(unidade.getHorarioFechamento())) {
            throw new IllegalArgumentException("A consulta deve ser marcada dentro do horário de funcionamento da unidade.");
        }
    }

    // Método para criar o histórico de patologias do paciente
    public void adicionarPatologiaAoHistorico(String patologia) {
        String dataConsultaFormatada = dataConsulta.toString(); // Formata a data para o histórico
        paciente.adicionarPatologia(patologia, dataConsultaFormatada);
    }

    // Getters e Setters
    public int getId() {
        return id; // Retorna o ID do agendamento
    }

    public void setId(int id) {
        this.id = id; // Permite a modificação do ID
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public LocalDate getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(LocalDate dataConsulta) {
        this.dataConsulta = dataConsulta;
        validarDataHoraConsulta(); // Revalida a data e hora quando são alteradas
    }

    public LocalTime getHoraConsulta() {
        return horaConsulta;
    }

    public void setHoraConsulta(LocalTime horaConsulta) {
        this.horaConsulta = horaConsulta;
        validarDataHoraConsulta(); // Revalida a data e hora quando são alteradas
    }

    public TipoConsulta getTipoConsulta() {
        return tipoConsulta;
    }

    public void setTipoConsulta(TipoConsulta tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }

    public String getLocalCirurgia() {
        return localCirurgia;
    }

    public void setLocalCirurgia(String localCirurgia) {
        if (this.tipoConsulta == TipoConsulta.CIRURGIA) {
            this.localCirurgia = localCirurgia;
        } else {
            throw new IllegalArgumentException("Local de cirurgia só pode ser definido para consultas do tipo CIRURGIA.");
        }
    }
}
