package com.dragenda.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class PessoaTest {

    // Classe concreta para testar a classe abstrata Pessoa
    class PessoaConcreta extends Pessoa {
        public PessoaConcreta(String nomeCompleto, String cpf) {
            super(nomeCompleto, cpf);
        }

        @Override
        public String getTipoPessoa() {
            return "Concreta";
        }
    }

    // Teste para verificar a criação de uma instância de Pessoa
    @Test
    void testPessoaCreation() {
        Pessoa pessoa = new PessoaConcreta("João Silva", "12345678901");
        assertEquals("João Silva", pessoa.getNomeCompleto());
        assertEquals("12345678901", pessoa.getCpf());
    }

    // Teste para verificar a alteração do nome completo de uma Pessoa
    @Test
    void testSetNomeCompleto() {
        Pessoa pessoa = new PessoaConcreta("João Silva", "12345678901");
        pessoa.setNomeCompleto("Maria Oliveira");
        assertEquals("Maria Oliveira", pessoa.getNomeCompleto());
    }

    // Teste para verificar a alteração do CPF de uma Pessoa
    @Test
    void testSetCpf() {
        Pessoa pessoa = new PessoaConcreta("João Silva", "12345678901");
        pessoa.setCpf("10987654321");
        assertEquals("10987654321", pessoa.getCpf());
    }

    // Teste para verificar a exceção ao definir um CPF inválido
    @Test
    void testSetInvalidCpf() {
        Pessoa pessoa = new PessoaConcreta("João Silva", "12345678901");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            pessoa.setCpf("123");
        });
        assertEquals("CPF inválido.", exception.getMessage());
    }
}