package com.dragenda.model;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class UnidadeTest {

    // Caso de teste para o construtor da classe Unidade que inclui um ID
    @Test
    public void testUnidadeConstructorWithId() {
        // Cria uma lista de dias de funcionamento
        List<String> diasFuncionamento = Arrays.asList("Segunda", "Terça", "Quarta");
        // Cria um objeto Unidade com um ID
        Unidade unidade = new Unidade(1, "Unidade A", "Rua A", LocalTime.of(8, 0), LocalTime.of(18, 0), diasFuncionamento);

        // Verifica se o objeto Unidade foi criado corretamente
        assertEquals(1, unidade.getId());
        assertEquals("Unidade A", unidade.getNome());
        assertEquals("Rua A", unidade.getEndereco());
        assertEquals(LocalTime.of(8, 0), unidade.getHorarioAbertura());
        assertEquals(LocalTime.of(18, 0), unidade.getHorarioFechamento());
        assertEquals(diasFuncionamento, unidade.getDiasFuncionamento());
    }

    // Caso de teste para o construtor da classe Unidade que não inclui um ID
    @Test
    public void testUnidadeConstructorWithoutId() {
        // Cria uma lista de dias de funcionamento
        List<String> diasFuncionamento = Arrays.asList("Segunda", "Terça", "Quarta");
        // Cria um objeto Unidade sem um ID
        Unidade unidade = new Unidade("Unidade B", "Rua B", LocalTime.of(9, 0), LocalTime.of(17, 0), diasFuncionamento);

        // Verifica se o objeto Unidade foi criado corretamente
        assertEquals("Unidade B", unidade.getNome());
        assertEquals("Rua B", unidade.getEndereco());
        assertEquals(LocalTime.of(9, 0), unidade.getHorarioAbertura());
        assertEquals(LocalTime.of(17, 0), unidade.getHorarioFechamento());
        assertEquals(diasFuncionamento, unidade.getDiasFuncionamento());
    }

    // Caso de teste para os setters e getters da classe Unidade
    @Test
    public void testSettersAndGetters() {
        // Cria um objeto Unidade
        Unidade unidade = new Unidade("Unidade C", "Rua C", LocalTime.of(10, 0), LocalTime.of(16, 0), Arrays.asList("Quinta", "Sexta"));

        // Define novos valores usando os setters
        unidade.setId(2);
        unidade.setNome("Unidade D");
        unidade.setEndereco("Rua D");
        unidade.setHorarioAbertura(LocalTime.of(7, 0));
        unidade.setHorarioFechamento(LocalTime.of(15, 0));
        unidade.setDiasFuncionamento(Arrays.asList("Sábado", "Domingo"));

        // Verifica se os getters retornam os valores corretos
        assertEquals(2, unidade.getId());
        assertEquals("Unidade D", unidade.getNome());
        assertEquals("Rua D", unidade.getEndereco());
        assertEquals(LocalTime.of(7, 0), unidade.getHorarioAbertura());
        assertEquals(LocalTime.of(15, 0), unidade.getHorarioFechamento());
        assertEquals(Arrays.asList("Sábado", "Domingo"), unidade.getDiasFuncionamento());
    }
}