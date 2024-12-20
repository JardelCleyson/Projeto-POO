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

    @BeforeEach
    public void setUp() {
        unidade = new Unidade(5, "teste", "address", LocalTime.of(9, 0), LocalTime.of(17, 0), new ArrayList<>());
        medico = new Medico(5, "Dr. John Doe", "123.456.789-00", "CRM12345", "Cardiologia", unidade);
    }

    @Test
    public void testGetTipoPessoa() {
        assertEquals("MÉDICO", medico.getTipoPessoa());
    }

    @Test
    public void testValidarCRM() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            medico = new Medico(4, "Dr. Jane Doe", "987.654.321-00", "", "Pediatria", unidade);
        });
        assertEquals("CRM cannot be empty", exception.getMessage());
    }

    @Test
    public void testSetCrm() {
        medico.setCrm("CRM54321");
        assertEquals("CRM54321", medico.getCrm());
    }

    @Test
    public void testSetCrmInvalid() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            medico.setCrm("");
        });
        assertEquals("CRM cannot be empty", exception.getMessage());
    }

    @Test
    public void testSetUnidadeVinculada() {
        Unidade novaUnidade = new Unidade(2, "Clínica Nova", "new address", LocalTime.of(8, 0), LocalTime.of(18, 0), new ArrayList<>());
        medico.setUnidadeVinculada(novaUnidade);
        assertEquals(novaUnidade, medico.getUnidadeVinculada());
    }

    @Test
    public void testSetUnidadeVinculadaNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            medico.setUnidadeVinculada(null);
        });
        assertEquals("Unidade cannot be null", exception.getMessage());
    }

    @Test
    public void testGetId() {
        assertEquals(1, medico.getId());
    }

    @Test
    public void testSetId() {
        medico.setId(2);
        assertEquals(2, medico.getId());
    }

    @Test
    public void testGetEspecialidade() {
        assertEquals("Cardiologia", medico.getEspecialidade());
    }

    @Test
    public void testSetEspecialidade() {
        medico.setEspecialidade("Neurologia");
        assertEquals("Neurologia", medico.getEspecialidade());
    }
}