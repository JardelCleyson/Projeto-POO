package com.dragenda.model; 

import org.junit.jupiter.api.BeforeEach; 
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

class AgendamentoTest {

    private Unidade unidade;
    private Paciente paciente;
    private Medico medico;

    @BeforeEach
    void setUp() {
        // Inicializando objetos fictícios para os testes
        unidade = new Unidade("Clínica X", List.of("MONDAY", "TUESDAY", "WEDNESDAY"), 
                              LocalTime.of(8, 0), LocalTime.of(18, 0));
        paciente = new Paciente("João da Silva");
        medico = new Medico("Dr. Ana", "12345");
    }

    @Test
    void deveCriarAgendamentoValido() {
        // Cenário válido: segunda-feira, 10h dentro do horário de funcionamento
        Agendamento agendamento = new Agendamento(
                1, unidade, paciente, medico, 
                LocalDate.of(2024, 10, 28), LocalTime.of(10, 0), 
                Agendamento.TipoConsulta.ROTINA
        );

        assertNotNull(agendamento);
        assertEquals(1, agendamento.getId());
        assertEquals(LocalDate.of(2024, 10, 28), agendamento.getDataConsulta());
        assertEquals(LocalTime.of(10, 0), agendamento.getHoraConsulta());
    }

    @Test
    void deveLancarExcecaoParaHorarioForaDoFuncionamento() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Agendamento(
                2, unidade, paciente, medico, 
                LocalDate.of(2024, 10, 28), LocalTime.of(19, 0), // Fora do horário
                Agendamento.TipoConsulta.ROTINA
            );
        });

        assertEquals("A consulta deve ser marcada dentro do horário de funcionamento da unidade.", 
                     exception.getMessage());
    }

    @Test
    void deveLancarExcecaoParaDiaForaDoFuncionamento() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Agendamento(
                3, unidade, paciente, medico, 
                LocalDate.of(2024, 10, 29), LocalTime.of(10, 0), // Terça, fora do dia permitido
                Agendamento.TipoConsulta.ROTINA
            );
        });

        assertEquals("A unidade não funciona no dia TUESDAY.", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoLocalCirurgiaInvalido() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Agendamento(
                4, unidade, paciente, medico, 
                LocalDate.of(2024, 10, 28), LocalTime.of(10, 0), 
                Agendamento.TipoConsulta.RETORNO, "Bloco A"
            );
        });

        assertEquals("Local de cirurgia só pode ser definido para consultas do tipo CIRURGIA.", 
                     exception.getMessage());
    }

    @Test
    void deveCriarAgendamentoDeCirurgiaComLocalValido() {
        Agendamento agendamento = new Agendamento(
                5, unidade, paciente, medico, 
                LocalDate.of(2024, 10, 28), LocalTime.of(10, 0), 
                Agendamento.TipoConsulta.CIRURGIA, "Bloco A"
        );

        assertNotNull(agendamento);
        assertEquals("Bloco A", agendamento.getLocalCirurgia());
    }
}
