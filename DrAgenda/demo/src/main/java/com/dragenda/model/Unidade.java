package com.dragenda.model;

import java.time.LocalTime;
import java.util.List;

public class Unidade {
    private int id; // Adicionando um atributo para ID da Unidade
    private String nome; // Nome da Unidade
    private String endereco; // Endereço da Unidade
    private LocalTime horarioAbertura; // Horário de Abertura
    private LocalTime horarioFechamento; // Horário de Fechamento
    private List<String> diasFuncionamento; // Dias de Funcionamento

    // Construtor com ID
    public Unidade(int id, String nome, String endereco, LocalTime horarioAbertura, LocalTime horarioFechamento, List<String> diasFuncionamento) {
        this.id = id; // Inicializa o ID
        this.nome = nome; // Inicializa o Nome
        this.endereco = endereco; // Inicializa o Endereço
        this.horarioAbertura = horarioAbertura; // Inicializa o Horário de Abertura
        this.horarioFechamento = horarioFechamento; // Inicializa o Horário de Fechamento
        this.diasFuncionamento = diasFuncionamento; // Inicializa os Dias de Funcionamento
    }

    // Construtor sem ID (para inserção)
    public Unidade(String nome, String endereco, LocalTime horarioAbertura, LocalTime horarioFechamento, List<String> diasFuncionamento) {
        this.nome = nome; // Inicializa o Nome
        this.endereco = endereco; // Inicializa o Endereço
        this.horarioAbertura = horarioAbertura; // Inicializa o Horário de Abertura
        this.horarioFechamento = horarioFechamento; // Inicializa o Horário de Fechamento
        this.diasFuncionamento = diasFuncionamento; // Inicializa os Dias de Funcionamento
    }

    // Getters e Setters
    public int getId() {
        return id; // Retorna o ID
    }

    public void setId(int id) {
        this.id = id; // Define o ID
    }

    public String getNome() {
        return nome; // Retorna o Nome
    }

    public void setNome(String nome) {
        this.nome = nome; // Define o Nome
    }

    public String getEndereco() {
        return endereco; // Retorna o Endereço
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco; // Define o Endereço
    }

    public LocalTime getHorarioAbertura() {
        return horarioAbertura; // Retorna o Horário de Abertura
    }

    public void setHorarioAbertura(LocalTime horarioAbertura) {
        this.horarioAbertura = horarioAbertura; // Define o Horário de Abertura
    }

    public LocalTime getHorarioFechamento() {
        return horarioFechamento; // Retorna o Horário de Fechamento
    }

    public void setHorarioFechamento(LocalTime horarioFechamento) {
        this.horarioFechamento = horarioFechamento; // Define o Horário de Fechamento
    }

    public List<String> getDiasFuncionamento() {
        return diasFuncionamento; // Retorna os Dias de Funcionamento
    }

    public void setDiasFuncionamento(List<String> diasFuncionamento) {
        this.diasFuncionamento = diasFuncionamento; // Define os Dias de Funcionamento
    }
}
