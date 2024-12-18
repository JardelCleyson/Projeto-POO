package com.dragenda.model;

import java.util.ArrayList;
import java.util.List;

public class Paciente extends Pessoa {
    private int id; // Adicionado para ID do paciente
    private Unidade unidadePreferencia;
    private List<String> historicoPatologias;

    public Paciente(int id, String nomeCompleto, String cpf, Unidade unidadePreferencia) {
        super(nomeCompleto, cpf);
        this.id = id; // Inicializa o ID
        this.unidadePreferencia = unidadePreferencia;
        this.historicoPatologias = new ArrayList<>();
    }

    @Override
    public String getTipoPessoa() {
        return "PACIENTE";
    }

    // Método para adicionar patologia ao histórico
    public void adicionarPatologia(String patologia, String dataConsulta) {
        String registro = "Consulta dia " + dataConsulta + " - Patologia: " + patologia;
        historicoPatologias.add(registro);
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "id=" + id +
                ", nomeCompleto='" + getNomeCompleto() + '\'' +
                ", cpf='" + getCpf() + '\'' +
                ", unidadePreferencia=" + unidadePreferencia +
                ", historicoPatologias=" + historicoPatologias +
                '}';
    }

    // Getters e Setters
    public int getId() { // Método getId adicionado
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Unidade getUnidadePreferencia() {
        return unidadePreferencia;
    }

    public void setUnidadePreferencia(Unidade unidadePreferencia) {
        this.unidadePreferencia = unidadePreferencia;
    }

    public List<String> getHistoricoPatologias() {
        return historicoPatologias;
    }
}
