package com.dragenda.model;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MedicoTest {

    private Medico medico;
    private Unidade unidade;

    // Método que é executado antes de cada teste para configurar os objetos necessários
    @BeforeEach
    public void setUp() {
        unidade = new Unidade(12, "Saudemais", "address", LocalTime.of(9, 0), LocalTime.of(17, 0), new ArrayList<>());
        medico = new Medico(3, "John Doe", "12345678900", "12345", "Cardiologia", unidade);
    }

    // Teste para verificar o tipo de pessoa do médico
    @Test
    public void testGetTipoPessoa() {
        assertEquals("MÉDICO", medico.getTipoPessoa());
    }

    // Teste para validar o CRM do médico, esperando uma exceção se o CRM for inválido
    @Test
    public void testValidarCRM() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            medico = new Medico(4, "Jane Doe", "98765432100", "54321", "Pediatria", unidade);
        });
        assertEquals("CRM cannot be empty", exception.getMessage());
    }

    // Teste para verificar a configuração do CRM do médico
    @Test
    public void testSetCrm() {
        medico.setCrm("54321");
        assertEquals("54321", medico.getCrm());
    }

    // Teste para verificar a configuração de um CRM inválido, esperando uma exceção
    @Test
    public void testSetCrmInvalid() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            medico.setCrm("");
        });
        assertEquals("CRM cannot be empty", exception.getMessage());
    }

    // Teste para verificar a configuração de uma nova unidade vinculada ao médico
    @Test
    public void testSetUnidadeVinculada() {
        Unidade novaUnidade = new Unidade(2, "Clínica Nova", "new address", LocalTime.of(8, 0), LocalTime.of(18, 0), new ArrayList<>());
        medico.setUnidadeVinculada(novaUnidade);
        assertEquals(novaUnidade, medico.getUnidadeVinculada());
    }

    // Teste para verificar a configuração de uma unidade nula, esperando uma exceção
    @Test
    public void testSetUnidadeVinculadaNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            medico.setUnidadeVinculada(null);
        });
        assertEquals("Unidade cannot be null", exception.getMessage());
    }

    // Teste para verificar o ID do médico
    @Test
    public void testGetId() {
        assertEquals(1, medico.getId());
    }

    // Teste para verificar a configuração do ID do médico
    @Test
    public void testSetId() {
        medico.setId(2);
        assertEquals(2, medico.getId());
    }

    // Teste para verificar a especialidade do médico
    @Test
    public void testGetEspecialidade() {
        assertEquals("Cardiologia", medico.getEspecialidade());
    }

    // Teste para verificar a configuração da especialidade do médico
    @Test
    public void testSetEspecialidade() {
        medico.setEspecialidade("Neurologia");
        assertEquals("Neurologia", medico.getEspecialidade());
    }
}