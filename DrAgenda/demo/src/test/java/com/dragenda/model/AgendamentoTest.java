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

    @BeforeEach
    public void setUp() {
        unidade = new Unidade(1, "Unidade A", "Endereço A", LocalTime.of(8, 0), LocalTime.of(18, 0), Arrays.asList("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY"));
        unidade.setDiasFuncionamento(Arrays.asList("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY"));
        unidade.setHorarioAbertura(LocalTime.of(8, 0));
        unidade.setHorarioFechamento(LocalTime.of(18, 0));

        paciente = new Paciente(1, "Paciente A", "12345678911", unidade);
        medico = new Medico(1, "Medico A", "98765432121", "12354", "Especialidade A", unidade);
    }

    @Test
    public void testAgendamentoValido() {
        LocalDate dataConsulta = LocalDate.of(2023, 10, 2); // Monday
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

    @Test
    public void testAgendamentoInvalidoDiaSemana() {
        LocalDate dataConsulta = LocalDate.of(2023, 10, 1); // Sunday
        LocalTime horaConsulta = LocalTime.of(10, 0);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Agendamento(1, unidade, paciente, medico, dataConsulta, horaConsulta, Agendamento.TipoConsulta.ROTINA);
        });

        assertEquals("A unidade não funciona no dia SUNDAY.", exception.getMessage());
    }

    @Test
    public void testAgendamentoInvalidoHorario() {
        LocalDate dataConsulta = LocalDate.of(2023, 10, 2); // Monday
        LocalTime horaConsulta = LocalTime.of(7, 0); // Antes da abertura da unidade

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Agendamento(1, unidade, paciente, medico, dataConsulta, horaConsulta, Agendamento.TipoConsulta.ROTINA);
        });

        assertEquals("A consulta deve ser marcada dentro do horário de funcionamento da unidade.", exception.getMessage());
    }

    @Test
    public void testAgendamentoCirurgiaValido() {
        LocalDate dataConsulta = LocalDate.of(2023, 10, 2); // Monday
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

    @Test
    public void testAgendamentoCirurgiaInvalido() {
        LocalDate dataConsulta = LocalDate.of(2023, 10, 2); // Monday
        LocalTime horaConsulta = LocalTime.of(10, 0);
        String localCirurgia = "Sala 1";

        // Criando agendamento com tipo ROTINA e local de cirurgia
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Agendamento(1, unidade, paciente, medico, dataConsulta, horaConsulta, Agendamento.TipoConsulta.CIRURGIA, localCirurgia);
        });

        assertEquals("Local de cirurgia só pode ser definido para consultas do tipo CIRURGIA.", exception.getMessage());
    }
}
