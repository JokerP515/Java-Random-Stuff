package med.voll.api.domain.medico;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import jakarta.persistence.EntityManager;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.paciente.DatosRegistroPaciente;
import med.voll.api.domain.paciente.Paciente;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Elegir medico aleatorio disponible en la fecha, devuelve null si el medico existe pero no está disponible")
    void testElegirMedicoAleatorioDisponibleEnLaFechaEscenario1() {
        // given o arrange
        LocalDateTime lunesSiguienteALas10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,
                0);

        Medico medico = registrarMedico("medico 1", "medico@gmail.com", "12879", Especialidad.CARDIOLOGIA);
        Paciente paciente = registrarPaciente("paciente 1", "paciente@gmail.com", "45678");
        registrarConsulta(medico, paciente, lunesSiguienteALas10);

        // when o act
        Medico medicoLibre = medicoRepository.elegirMedicoAleatorioDisponibleEnLaFecha(Especialidad.CARDIOLOGIA,
                lunesSiguienteALas10);

        // then o assert
        assertThat(medicoLibre).isNull();
    }
    @Test
    @DisplayName("Elegir medico aleatorio disponible en la fecha, devuelve el medico si está disponible")
    void testElegirMedicoAleatorioDisponibleEnLaFechaEscenario2(){

        //given o arrange
        LocalDateTime lunesSiguienteALas10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,
                0);

        Medico medico = registrarMedico("medico 1", "medico@gmail.com", "12879", Especialidad.CARDIOLOGIA);

        //when o act
        Medico medicoLibre = medicoRepository.elegirMedicoAleatorioDisponibleEnLaFecha(Especialidad.CARDIOLOGIA,
                lunesSiguienteALas10);

        //then o assert
        assertThat(medicoLibre).isEqualTo(medico);
    }

    private void registrarConsulta(Medico medico, Paciente paciente, LocalDateTime fecha) {
        entityManager.persist(new Consulta(null, medico, paciente, fecha, null));
    }

    private Medico registrarMedico(String nombre, String email, String documento, Especialidad especialidad) {
        Medico medico = new Medico(datosMedico(nombre, email, documento, especialidad));
        entityManager.persist(medico);
        return medico;
    }

    private Paciente registrarPaciente(String nombre, String email, String documento) {
        Paciente paciente = new Paciente(datosPaciente(nombre, email, documento));
        entityManager.persist(paciente);
        return paciente;
    }

    private DatosRegistroMedico datosMedico(String nombre, String email, String documento, Especialidad especialidad) {
        return new DatosRegistroMedico(
                nombre,
                email,
                "123456",
                documento,
                especialidad,
                datosDireccion());
    }

    private DatosRegistroPaciente datosPaciente(String nombre, String email, String documento) {
        return new DatosRegistroPaciente(
                nombre,
                email,
                "123458",
                documento,
                datosDireccion());
    }

    private DatosDireccion datosDireccion() {
        return new DatosDireccion(
                "calle falsa 123",
                "distrito 1",
                "ciudad 1",
                "1238",
                "abc");
    }
}
