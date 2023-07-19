package turnosMedicos

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class TurnoSpec extends Specification implements DomainUnitTest<Turno> {

    DateTimeFormatter dateFormatter;
    DateTimeFormatter dateTimeFormatter;
    Medico medico;
    Paciente paciente;
    LocalDate fechaDeNacimiento;

    def setup() {
        dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        medico = new Medico("Juan", "Gomez", "36456612", "Clinico", "M12873BD")
        fechaDeNacimiento = LocalDate.parse("16/08/2002", dateFormatter);
        paciente = new Paciente("Maria", "Perez", "39805131", "juanPerez@gmail.com", fechaDeNacimiento)
    }

    def cleanup() {
    }

    void "US1.1 - Reserva de turno - Reservar turno exitosamente"() {

        LocalDateTime fechaDelTurno = LocalDateTime.parse("16/08/2023 14:00:00", dateTimeFormatter);

        given: "Dado que soy un paciente y un médico traumatólogo “Juan Gomez” y un turno del médico"
        Turno turno = medico.crearTurno(fechaDelTurno, "Hospital Italiano", 30)

        when: "cuando reservo el turno"
        paciente.reservarTurno(turno)

        then: "veo el turno reservado a mi nombre"
        assert paciente.turnos.size() == 1
        assert turno.paciente != null
        assert turno.paciente.nombre == paciente.nombre
        assert turno.paciente.apellido == paciente.apellido
    }

    void "US1.2 - Reserva de turno - Reservar segundo turno de misma especialidad falla"() {

        LocalDateTime fechaDelTurno = LocalDateTime.parse("16/08/2023 14:00:00", dateTimeFormatter);
        LocalDateTime fechaDelOtroTurno = LocalDateTime.parse("17/08/2023 14:00:00", dateTimeFormatter);
        Medico otroMedico = new Medico("Juan", "Gomez", "36456612", "Clinico", "M12873BD")

        given: "Dado un paciente, un turno reservado a mi nombre, y un turno de la misma especialidad pero de otro medico"
        Turno turno = medico.crearTurno(fechaDelTurno, "Hospital Italiano", 30)
        Turno otroTurno = otroMedico.crearTurno(fechaDelOtroTurno, "Hospital Italiano", 30)
        paciente.reservarTurno(turno)

        when: "quiero sacar otro turno con el otro médico traumatólogo antes de que pase mi turno"
        paciente.reservarTurno(otroTurno)

        then: "me muestra un error que me dice que no puedo tener más de un turno por especialidad"
        thrown(ReservaDeTurnosException)
    }

    void "US2.0 - Cancelar turno - Dado que soy un paciente, tengo un turno reservado, cuando cancelo el turno, entonces no veo más ese turno registrado."() {

        LocalDateTime fechaDelTurno = LocalDateTime.parse("16/08/2023 14:00:00", dateTimeFormatter);
        Turno turno = medico.crearTurno(fechaDelTurno, "Hospital Italiano", 30)

        given: "Dado un paciente, un medico y un turno reservado por un paciente"
        paciente.reservarTurno(turno)

        when: "cuando el paciente cancela el turno"
        paciente.cancelarTurno(turno)

        then: "no veo mas el turno registrado"
        assert paciente.turnos.size() == 0
        assert turno.paciente == null
    }

    void "US2.1 - Cancelar turno - Dado que soy un paciente, tengo un turno reservado, cuando cancelo el turno reservado, entonces otro paciente puede reservarlo."() {

        LocalDateTime fechaDelTurno = LocalDateTime.parse("16/08/2023 14:00:00", dateTimeFormatter);

        Paciente otroPaciente = new Paciente("Romina", "Gonzalez", "48516356", "rominaGonzalez@gmail.com", fechaDeNacimiento)
        Turno turno = medico.crearTurno(fechaDelTurno, "Hospital Italiano", 30)

        given: "Dado un paciente, un medico y un turno reservado por el paciente"
        paciente.reservarTurno(turno)

        when: "cuando otro paciente reserva el mismo turno"
        paciente.cancelarTurno(turno)
        otroPaciente.reservarTurno(turno)

        then: "veo el turno reservado"
        assert otroPaciente.turnos.size() == 1
        assert paciente.turnos.size() == 0
        assert turno.paciente != null
    }

    void "US5.1 - Bloquear paciente - Dado que soy un paciente y cancelo el turno del médico traumatólogo en menos de 72 horas, cuando reservo un turno del médico traumatólogo el mismo mes, entonces me muestra un error que no puedo tomar turnos de este médico durante este mes."() {

        LocalDateTime diaDeHoy = LocalDateTime.parse("13/08/2023 14:00:01", dateTimeFormatter);

        LocalDateTime fechaDelTurno = LocalDateTime.parse("16/08/2023 14:00:00", dateTimeFormatter);
        LocalDateTime fechaDelOtroTurno = LocalDateTime.parse("17/08/2023 14:00:00", dateTimeFormatter);

        Turno turno = medico.crearTurno(fechaDelTurno, "Hospital Italiano", 30)
        Turno otroTurnoElMisMes = medico.crearTurno(fechaDelOtroTurno, "Hospital Italiano", 30)

        given: "Dado un paciente y un turno cancelado por el paciente en menos de 72 horas"
        paciente.reservarTurno(turno)
        paciente.cancelarTurno(turno, diaDeHoy)

        when: "cuando reservo un turno del médico traumatólogo el mismo mes"
        paciente.reservarTurno(otroTurnoElMisMes)

        then: "entonces me muestra un error que no puedo tomar turnos de este médico durante este mes"
        thrown(PacienteBloqueadoException)
    }

}
