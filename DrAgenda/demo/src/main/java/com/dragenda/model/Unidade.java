package com.dragenda.model;

import java.time.LocalTime;
import java.util.List;

public class Unidade {
    private int id; 
    private String nome; 
    private String endereco; 
    private LocalTime horarioAbertura; 
    private LocalTime horarioFechamento; 
    private List<String> diasFuncionamento; 

    // Construtor com ID
    public Unidade(int id, String nome, String endereco, LocalTime horarioAbertura, LocalTime horarioFechamento, List<String> diasFuncionamento) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.horarioAbertura = horarioAbertura;
        this.horarioFechamento = horarioFechamento;
        this.diasFuncionamento = diasFuncionamento;
    }

    // Construtor sem ID (para inserção)
    public Unidade(String nome, String endereco, LocalTime horarioAbertura, LocalTime horarioFechamento, List<String> diasFuncionamento) {
        this.nome = nome;
        this.endereco = endereco;
        this.horarioAbertura = horarioAbertura;
        this.horarioFechamento = horarioFechamento;
        this.diasFuncionamento = diasFuncionamento;
    }

    // Sobrescrevendo o método toString()
    @Override
    public String toString() {
        return "Unidade{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", endereco='" + endereco + '\'' +
                ", horarioAbertura=" + horarioAbertura +
                ", horarioFechamento=" + horarioFechamento +
                ", diasFuncionamento=" + diasFuncionamento +
                '}';
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public LocalTime getHorarioAbertura() {
        return horarioAbertura;
    }

    public void setHorarioAbertura(LocalTime horarioAbertura) {
        this.horarioAbertura = horarioAbertura;
    }

    public LocalTime getHorarioFechamento() {
        return horarioFechamento;
    }

    public void setHorarioFechamento(LocalTime horarioFechamento) {
        this.horarioFechamento = horarioFechamento;
    }

    public List<String> getDiasFuncionamento() {
        return diasFuncionamento;
    }

    public void setDiasFuncionamento(List<String> diasFuncionamento) {
        this.diasFuncionamento = diasFuncionamento;
    }
}
