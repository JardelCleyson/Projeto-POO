package com.dragenda.model;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UnidadeTest {

    private Unidade unidade;

    @BeforeEach
    void setup() {
        unidade = new Unidade(
            1, 
            "Clínica Central", 
            "Rua das Flores, 123", 
            LocalTime.of(8, 0), 
            LocalTime.of(18, 0), 
            List.of("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY")
        );
    }

    @Test
    void testConstrutorComId() {
        assertEquals(1, unidade.getId());
        assertEquals("Clínica Central", unidade.getNome());
        assertEquals("Rua das Flores, 123", unidade.getEndereco());
        assertEquals(LocalTime.of(8, 0), unidade.getHorarioAbertura());
        assertEquals(LocalTime.of(18, 0), unidade.getHorarioFechamento());
        assertIterableEquals(List.of("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY"), unidade.getDiasFuncionamento());
    }

    @Test
    void testSetNome() {
        unidade.setNome("Nova Clínica");
        assertEquals("Nova Clínica", unidade.getNome());
    }

    @Test
    void testSetEndereco() {
        unidade.setEndereco("Avenida Principal, 456");
        assertEquals("Avenida Principal, 456", unidade.getEndereco());
    }

    @Test
    void testSetHorarioAbertura() {
        unidade.setHorarioAbertura(LocalTime.of(9, 0));
        assertEquals(LocalTime.of(9, 0), unidade.getHorarioAbertura());
    }

    @Test
    void testSetHorarioFechamento() {
        unidade.setHorarioFechamento(LocalTime.of(17, 0));
        assertEquals(LocalTime.of(17, 0), unidade.getHorarioFechamento());
    }

    @Test
    void testSetDiasFuncionamento() {
        unidade.setDiasFuncionamento(List.of("SATURDAY", "SUNDAY"));
        assertIterableEquals(List.of("SATURDAY", "SUNDAY"), unidade.getDiasFuncionamento());
    }

    @Test
    void testSetId() {
        unidade.setId(2);
        assertEquals(2, unidade.getId());
    }
}
