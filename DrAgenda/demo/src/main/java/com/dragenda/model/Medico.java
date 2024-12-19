package com.dragenda.model;

public class Medico extends Pessoa {
    private int id; // Adicionado para ID do médico
    private String crm;
    private String especialidade;
    private Unidade unidadeVinculada;

    public Medico(int id, String nomeCompleto, String cpf, String crm, String especialidade, Unidade unidadeVinculada) {
        super(nomeCompleto, cpf);
        this.id = id; // Inicializa o ID
        this.crm = crm;
        this.especialidade = especialidade;
        setUnidadeVinculada(unidadeVinculada); // Usa o setter para validação
        validarCRM(crm);
    }

    @Override
    public String getTipoPessoa() {
        return "MÉDICO";
    }

    private void validarCRM(String crm) { // Método para validar o CRM
        if (crm == null || crm.isEmpty()) {
            throw new IllegalArgumentException("CRM inválido.");
        }
    }

    @Override
    public String toString() { // Método toString modificado
        return "Medico{" +
                "id=" + id +
                ", nomeCompleto='" + getNomeCompleto() + '\'' +
                ", cpf='" + getCpf() + '\'' +
                ", crm='" + crm + '\'' +
                ", especialidade='" + especialidade + '\'' +
                ", unidadeVinculada=" + unidadeVinculada.getNome() +
                '}';
    }

    // Getters e Setters
    public int getId() { // Método getId adicionado
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        validarCRM(crm);
        this.crm = crm;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public Unidade getUnidadeVinculada() {
        return unidadeVinculada;
    }

    public void setUnidadeVinculada(Unidade unidadeVinculada) {
        if (unidadeVinculada == null) {
            throw new IllegalArgumentException("Unidade vinculada não pode ser nula.");
        }
        this.unidadeVinculada = unidadeVinculada;
    }
}
