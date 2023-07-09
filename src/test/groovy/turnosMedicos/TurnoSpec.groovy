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

    def setup() {
        dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        medico = new Medico("Maria", "Gonzalez", "36456612", "Clinico", "M12873BD")

        LocalDate fechaDeNacimiento = LocalDate.parse("16/08/2002", dateFormatter);

        paciente = new Paciente("Juan", "Perez", "39805131", "juanPerez@gmail.com", fechaDeNacimiento)
    }

    def cleanup() {
    }

    void "H2.1 - Paciente reserva un turno de un medico"() {

        LocalDateTime fechaDelTurno = LocalDateTime.parse("16/08/2023 14:00:00", dateTimeFormatter);

        given: "Dado un paciente, un medico y un turno"
            Turno turno = medico.crearTurno(fechaDelTurno, "Hospital Italiano", 30)

        when: "cuando reservo un turno"
            paciente.reservarTurno(turno)

        then: "veo el turno reservado"
            assert paciente.turnos.size() == 1
            assert turno.paciente != null
    }

    void "H3.1 - Paciente reserva un turno de la misma especializacion el mismo mes da error"() {

        LocalDateTime fechaDelTurno = LocalDateTime.parse("16/08/2023 14:00:00", dateTimeFormatter);

        given: "Dado un paciente, un medico y un turno, e intento reservar un turno de la misma especialidad en el mismo mes"
            Turno turno = medico.crearTurno(fechaDelTurno, "Hospital Italiano", 30)
            paciente.reservarTurno(turno)

        when: "cuando reservo un turno"
            paciente.reservarTurno(turno)

        then: "veo un error en el turno"
            thrown(ReservaDeTurnosException)
    }

    void "H3.2 - Paciente reserva un turno de la misma especializacion con menos de mes de diferencia hacia atras"() {

        LocalDateTime fechaDelTurno = LocalDateTime.parse("16/08/2023 14:00:00", dateTimeFormatter);
        LocalDateTime fechaDelTurnoDelMesAnterior = LocalDateTime.parse("17/07/2023 14:00:00", dateTimeFormatter);

        given: "Dado un paciente, un medico y un turno, e intento reservar un turno de la misma especialidad en el mismo mes"
            Turno turno = medico.crearTurno(fechaDelTurno, "Hospital Italiano", 30)
            Turno turnoDelMesAnterior = medico.crearTurno(fechaDelTurnoDelMesAnterior, "Hospital Italiano", 30)
            paciente.reservarTurno(turno)

        when: "cuando reservo un turno"
            paciente.reservarTurno(turnoDelMesAnterior)

        then: "obtengo un error"
            thrown(ReservaDeTurnosException)
    }

    void "H3.3 - Paciente reserva un turno de la misma especializacion con menos de mes de diferencia hacia adelante"() {

        LocalDateTime fechaDelTurno = LocalDateTime.parse("16/08/2023 14:00:00", dateTimeFormatter);
        LocalDateTime fechaDelTurnoDelMesSiguiente = LocalDateTime.parse("15/09/2023 14:00:00", dateTimeFormatter);

        given: "Dado un paciente, un medico y un turno, e intento reservar un turno de la misma especialidad en el mismo mes"
            Turno turno = medico.crearTurno(fechaDelTurno, "Hospital Italiano", 30)
            Turno turnoDelMesSiguiente = medico.crearTurno(fechaDelTurnoDelMesSiguiente, "Hospital Italiano", 30)
            paciente.reservarTurno(turno)

        when: "cuando reservo un turno"
            paciente.reservarTurno(turnoDelMesSiguiente)

        then: "obtengo un error"
            thrown(ReservaDeTurnosException)
    }

    void "H4.1 - Paciente reserva un turno de la misma especializacion con mas de un mes de diferencia hacia atras"() {

        LocalDateTime fechaDelTurno = LocalDateTime.parse("16/08/2023 14:00:00", dateTimeFormatter);
        LocalDateTime fechaDelTurnoDelMesAnterior = LocalDateTime.parse("16/07/2023 14:00:00", dateTimeFormatter);

        given: "Dado un paciente, un medico y un turno, e intento reservar un turno de la misma especialidad en el mismo mes"
            Turno turno = medico.crearTurno(fechaDelTurno, "Hospital Italiano", 30)
            Turno turnoDelMesAnterior = medico.crearTurno(fechaDelTurnoDelMesAnterior, "Hospital Italiano", 30)
            paciente.reservarTurno(turno)

        when: "cuando reservo un turno"
            paciente.reservarTurno(turnoDelMesAnterior)

        then: "veo el turno reservado"
            assert paciente.turnos.size() == 2
            assert turno.paciente != null
            assert turnoDelMesAnterior.paciente != null
    }

    void "H5.1 - Paciente reserva un turno de la misma especializacion con mas de mes de diferencia hacia adelante"() {

        LocalDateTime fechaDelTurno = LocalDateTime.parse("16/08/2023 14:00:00", dateTimeFormatter);
        LocalDateTime fechaDelTurnoDelMesSiguiente = LocalDateTime.parse("17/09/2023 14:00:00", dateTimeFormatter);

        given: "Dado un paciente, un medico y un turno, e intento reservar un turno de la misma especialidad en el mismo mes"
            Turno turno = medico.crearTurno(fechaDelTurno, "Hospital Italiano", 30)
            Turno turnoDelMesSiguiente = medico.crearTurno(fechaDelTurnoDelMesSiguiente, "Hospital Italiano", 30)
            paciente.reservarTurno(turno)

        when: "cuando reservo un turno"
            paciente.reservarTurno(turnoDelMesSiguiente)

        then: "veo el turno reservado"
            assert paciente.turnos.size() == 2
            assert turno.paciente != null
            assert turnoDelMesSiguiente.paciente != null
    }

}
