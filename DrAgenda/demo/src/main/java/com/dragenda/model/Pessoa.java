package com.dragenda.model;

public abstract class Pessoa {
    private String nomeCompleto;
    private String cpf;

    public Pessoa(String nomeCompleto, String cpf) { // Construtor da classe Pessoa
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        validarCPF(cpf);
    }

    // Método abstrato que será implementado pelas classes filhas
    public abstract String getTipoPessoa();

    // Validação básica de CPF
    private void validarCPF(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            throw new IllegalArgumentException("CPF inválido.");
        }
        // Podemos adicionar uma lógica mais robusta de validação aqui.
    }
    
    @Override
    public String toString() { 
        return "Pessoa{" +
                "nomeCompleto='" + nomeCompleto + '\'' +
                ", cpf='" + cpf + '\'' +
                '}';
    }

    // Getters e Setters
    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        validarCPF(cpf);
        this.cpf = cpf;
    }
}
