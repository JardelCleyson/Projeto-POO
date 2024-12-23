package com.dragenda.model;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PacienteTest {

    private Paciente paciente;
    private Unidade unidade;

    // Método que é executado antes de cada teste
    @BeforeEach
    public void setUp() {
        unidade = new Unidade(1, "Teste 4", "Endereco Teste", LocalTime.of(8, 0), LocalTime.of(18, 0), List.of("Serviço 1", "Serviço 2"));
        paciente = new Paciente(1, "Jose Silva", "12345678900", unidade);
    }

    // Testa o método getTipoPessoa
    @Test
    public void testGetTipoPessoa() {
        assertEquals("PACIENTE", paciente.getTipoPessoa());
    }

    // Testa o método adicionarPatologia
    @Test
    public void testAdicionarPatologia() {
        paciente.adicionarPatologia("Gripe", "01/01/2023");
        List<String> historico = paciente.getHistoricoPatologias();
        assertEquals(1, historico.size());
        assertEquals("Consulta dia 01/01/2023 - Patologia: Gripe", historico.get(0));
    }

    // Testa o método getId
    @Test
    public void testGetId() {
        assertEquals(1, paciente.getId());
    }

    // Testa o método setId
    @Test
    public void testSetId() {
        paciente.setId(2);
        assertEquals(2, paciente.getId());
    }

    // Testa o método getUnidadePreferencia
    @Test
    public void testGetUnidadePreferencia() {
        assertEquals(unidade, paciente.getUnidadePreferencia());
    }

    // Testa o método setUnidadePreferencia
    @Test
    public void testSetUnidadePreferencia() {
        Unidade novaUnidade = new Unidade(2, "Nova Unidade", "Endereco Nova", LocalTime.of(9, 0), LocalTime.of(17, 0), List.of("Serviço 3", "Serviço 4"));
        paciente.setUnidadePreferencia(novaUnidade);
        assertEquals(novaUnidade, paciente.getUnidadePreferencia());
    }

    // Testa o método getHistoricoPatologias
    @Test
    public void testGetHistoricoPatologias() {
        assertTrue(paciente.getHistoricoPatologias().isEmpty());
        paciente.adicionarPatologia("Gripe", "01/01/2023");
        assertFalse(paciente.getHistoricoPatologias().isEmpty());
    }
}