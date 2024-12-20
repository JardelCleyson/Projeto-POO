package com.dragenda.model;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;



public class UnidadeTest {

    @Test
    public void testUnidadeConstructorWithId() {
        List<String> diasFuncionamento = Arrays.asList("Segunda", "Terça", "Quarta");
        Unidade unidade = new Unidade(1, "Unidade A", "Rua A", LocalTime.of(8, 0), LocalTime.of(18, 0), diasFuncionamento);

        assertEquals(1, unidade.getId());
        assertEquals("Unidade A", unidade.getNome());
        assertEquals("Rua A", unidade.getEndereco());
        assertEquals(LocalTime.of(8, 0), unidade.getHorarioAbertura());
        assertEquals(LocalTime.of(18, 0), unidade.getHorarioFechamento());
        assertEquals(diasFuncionamento, unidade.getDiasFuncionamento());
    }

    @Test
    public void testUnidadeConstructorWithoutId() {
        List<String> diasFuncionamento = Arrays.asList("Segunda", "Terça", "Quarta");
        Unidade unidade = new Unidade("Unidade B", "Rua B", LocalTime.of(9, 0), LocalTime.of(17, 0), diasFuncionamento);

        assertEquals("Unidade B", unidade.getNome());
        assertEquals("Rua B", unidade.getEndereco());
        assertEquals(LocalTime.of(9, 0), unidade.getHorarioAbertura());
        assertEquals(LocalTime.of(17, 0), unidade.getHorarioFechamento());
        assertEquals(diasFuncionamento, unidade.getDiasFuncionamento());
    }

    @Test
    public void testSettersAndGetters() {
        Unidade unidade = new Unidade("Unidade C", "Rua C", LocalTime.of(10, 0), LocalTime.of(16, 0), Arrays.asList("Quinta", "Sexta"));

        unidade.setId(2);
        unidade.setNome("Unidade D");
        unidade.setEndereco("Rua D");
        unidade.setHorarioAbertura(LocalTime.of(7, 0));
        unidade.setHorarioFechamento(LocalTime.of(15, 0));
        unidade.setDiasFuncionamento(Arrays.asList("Sábado", "Domingo"));

        assertEquals(2, unidade.getId());
        assertEquals("Unidade D", unidade.getNome());
        assertEquals("Rua D", unidade.getEndereco());
        assertEquals(LocalTime.of(7, 0), unidade.getHorarioAbertura());
        assertEquals(LocalTime.of(15, 0), unidade.getHorarioFechamento());
        assertEquals(Arrays.asList("Sábado", "Domingo"), unidade.getDiasFuncionamento());
    }
}