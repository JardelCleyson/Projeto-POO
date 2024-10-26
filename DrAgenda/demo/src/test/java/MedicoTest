package com.dragenda.model;

import java.util.List;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



class MedicoTest {

    private Unidade unidade;

    @BeforeEach
    void setUp() {
        // Inicializa uma unidade válida para os testes
        unidade = new Unidade("Clínica Central", 
                              List.of("MONDAY", "TUESDAY", "WEDNESDAY"), 
                              LocalTime.of(8, 0), LocalTime.of(18, 0));
    }

    @Test
    void deveCriarMedicoComDadosValidos() {
        Medico medico = new Medico(
                1, "Dr. João Silva", "123.456.789-00", 
                "CRM12345", "Cardiologia", unidade
        );

        assertNotNull(medico);
        assertEquals(1, medico.getId());
        assertEquals("Dr. João Silva", medico.getNomeCompleto());
        assertEquals("CRM12345", medico.getCrm());
        assertEquals("Cardiologia", medico.getEspecialidade());
        assertEquals(unidade, medico.getUnidadeVinculada());
    }

    @Test
    void deveLancarExcecaoParaCRMVazio() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Medico(2, "Dr. Ana Sousa", "987.654.321-00", "", 
                       "Neurologia", unidade);
        });

        assertEquals("CRM inválido.", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoParaCRMNulo() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Medico(3, "Dr. Carlos Pereira", "111.222.333-44", null, 
                       "Ortopedia", unidade);
        });

        assertEquals("CRM inválido.", exception.getMessage());
    }

    @Test
    void devePermitirAlterarCRMValido() {
        Medico medico = new Medico(4, "Dr. Maria Lopes", "333.444.555-66", 
                                   "CRM9876", "Pediatria", unidade);
        medico.setCrm("CRM54321");

        assertEquals("CRM54321", medico.getCrm());
    }

    @Test
    void deveLancarExcecaoAoDefinirCRMVazio() {
        Medico medico = new Medico(5, "Dr. José Santos", "444.555.666-77", 
                                   "CRM1234", "Dermatologia", unidade);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            medico.setCrm("");
        });

        assertEquals("CRM inválido.", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoUnidadeVinculadaForNula() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Medico(6, "Dra. Clara Mendes", "555.666.777-88", 
                       "CRM6789", "Ginecologia", null);
        });

        assertEquals("Unidade vinculada não pode ser nula.", exception.getMessage());
    }
}
