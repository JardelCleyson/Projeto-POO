package com.dragenda.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PessoaTest {

    // Classe interna fictícia para testar a classe abstrata Pessoa
    static class PessoaFicticia extends Pessoa {
        public PessoaFicticia(String nomeCompleto, String cpf) {
            super(nomeCompleto, cpf);
        }

        @Override
        public String getTipoPessoa() {
            return "FICTICIA";
        }
    }

    private Pessoa pessoa;

    @BeforeEach
    void setup() {
        pessoa = new PessoaFicticia("Maria Oliveira", "12345678901");
    }

    @Test
    void testConstrutorPessoa() {
        assertEquals("Maria Oliveira", pessoa.getNomeCompleto());
        assertEquals("12345678901", pessoa.getCpf());
    }

    @Test
    void testSetNomeCompleto() {
        pessoa.setNomeCompleto("João Silva");
        assertEquals("João Silva", pessoa.getNomeCompleto());
    }

    @Test
    void testSetCpfValido() {
        pessoa.setCpf("09876543210");
        assertEquals("09876543210", pessoa.getCpf());
    }

    @Test
    void testSetCpfInvalido() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> pessoa.setCpf("12345")
        );
        assertEquals("CPF inválido.", exception.getMessage());
    }

    @Test
    void testConstrutorCpfInvalido() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new PessoaFicticia("Carlos Souza", "999")
        );
        assertEquals("CPF inválido.", exception.getMessage());
    }

    @Test
    void testGetTipoPessoa() {
        assertEquals("FICTICIA", pessoa.getTipoPessoa());
    }
}
