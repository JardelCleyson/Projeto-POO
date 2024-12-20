package com.dragenda.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AgendamentoTest {

    private Unidade unidade;
    private Paciente paciente;
    private Medico medico;

    // Método que é executado antes de cada teste para configurar os objetos necessários
    @BeforeEach
    public void setUp() {
        unidade = new Unidade(1, "Unidade A", "Endereço A", LocalTime.of(8, 0), LocalTime.of(18, 0), Arrays.asList("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY"));
        unidade.setDiasFuncionamento(Arrays.asList("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY"));
        unidade.setHorarioAbertura(LocalTime.of(8, 0));
        unidade.setHorarioFechamento(LocalTime.of(18, 0));

        paciente = new Paciente(1, "Paciente A", "12345678911", unidade);
        medico = new Medico(1, "Medico A", "98765432121", "12354", "Especialidade A", unidade);
    }

    // Teste para verificar se um agendamento válido é criado corretamente
    @Test
    public void testAgendamentoValido() {
        LocalDate dataConsulta = LocalDate.of(2025, 10, 2); // Segunda-feira
        LocalTime horaConsulta = LocalTime.of(10, 0);

        Agendamento agendamento = new Agendamento(1, unidade, paciente, medico, dataConsulta, horaConsulta, Agendamento.TipoConsulta.ROTINA);

        assertEquals(1, agendamento.getId());
        assertEquals(unidade, agendamento.getUnidade());
        assertEquals(paciente, agendamento.getPaciente());
        assertEquals(medico, agendamento.getMedico());
        assertEquals(dataConsulta, agendamento.getDataConsulta());
        assertEquals(horaConsulta, agendamento.getHoraConsulta());
        assertEquals(Agendamento.TipoConsulta.ROTINA, agendamento.getTipoConsulta());
    }

    // Teste para verificar se um agendamento em um dia inválido lança uma exceção
    @Test
    public void testAgendamentoInvalidoDiaSemana() {
        LocalDate dataConsulta = LocalDate.of(2025, 10, 1); 
        LocalTime horaConsulta = LocalTime.of(10, 0);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Agendamento agendamento = new Agendamento(1, unidade, paciente, medico, dataConsulta, horaConsulta, Agendamento.TipoConsulta.ROTINA);
        });

        assertEquals("A unidade não funciona no dia SUNDAY.", exception.getMessage());
    }

    // Teste para verificar se um agendamento fora do horário de funcionamento lança uma exceção
    @Test
    public void testAgendamentoInvalidoHorario() {
        LocalDate dataConsulta = LocalDate.of(2025, 10, 2); // Segunda-feira
        LocalTime horaConsulta = LocalTime.of(1, 0); // Antes da abertura da unidade

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Agendamento(1, unidade, paciente, medico, dataConsulta, horaConsulta, Agendamento.TipoConsulta.ROTINA);
        });

        assertEquals("A consulta deve ser marcada dentro do horário de funcionamento da unidade.", exception.getMessage());
    }

    // Teste para verificar se um agendamento de cirurgia válido é criado corretamente
    @Test
    public void testAgendamentoCirurgiaValido() {
        LocalDate dataConsulta = LocalDate.of(2025, 10, 2); // Segunda-feira
        LocalTime horaConsulta = LocalTime.of(10, 0);
        String localCirurgia = "Sala 1";

        Agendamento agendamento = new Agendamento(1, unidade, paciente, medico, dataConsulta, horaConsulta, Agendamento.TipoConsulta.CIRURGIA, localCirurgia);

        assertEquals(1, agendamento.getId());
        assertEquals(unidade, agendamento.getUnidade());
        assertEquals(paciente, agendamento.getPaciente());
        assertEquals(medico, agendamento.getMedico());
        assertEquals(dataConsulta, agendamento.getDataConsulta());
        assertEquals(horaConsulta, agendamento.getHoraConsulta());
        assertEquals(Agendamento.TipoConsulta.CIRURGIA, agendamento.getTipoConsulta());
        assertEquals(localCirurgia, agendamento.getLocalCirurgia());
    }

    // Teste para verificar se um agendamento de cirurgia inválido lança uma exceção
    @Test
    public void testAgendamentoCirurgiaInvalido() {
        LocalDate dataConsulta = LocalDate.of(2025, 10, 2); // Segunda-feira
        LocalTime horaConsulta = LocalTime.of(10, 0);
        String localCirurgia = "Sala 1";

        // Criando agendamento com tipo ROTINA e local de cirurgia
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Agendamento agendamento = new Agendamento(1, unidade, paciente, medico, dataConsulta, horaConsulta, Agendamento.TipoConsulta.CIRURGIA, localCirurgia);
        });

        assertEquals("Local de cirurgia só pode ser definido para consultas do tipo CIRURGIA.", exception.getMessage());
    }
}
