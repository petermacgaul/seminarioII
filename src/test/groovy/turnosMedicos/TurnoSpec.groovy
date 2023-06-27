package turnosMedicos

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class TurnoSpec extends Specification implements DomainUnitTest<Turno> {

    def setup() {
    }

    def cleanup() {
    }


    void "H2.1 - Paciente reserva un turno"() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        String fechaDeNacimientoStr = "16/08/2002";
        LocalDate fechaDeNacimiento = LocalDate.parse(fechaDeNacimientoStr, dateFormatter);

        String fechaDelTurnoStr = "16/08/2023 14:00:00";
        LocalDateTime fechaDelTurno = LocalDateTime.parse(fechaDelTurnoStr, dateTimeFormatter);

        given: "Dado un paciente, un medico y un turno"
            Paciente paciente = new Paciente("Juan", "Perez", "39805131", "juanPerez@gmail.com", fechaDeNacimiento)
            Medico medico = new Medico("Maria", "Gonzalez", "36456612", "Clinico", "M12873BD")
            Turno turno = medico.crearTurno(fechaDelTurno, "Hospital Italiano", 30)

        when: "cuando reservo un turno"
            paciente.reservarTurno(turno)

        then: "veo el turno reservado"
            assert paciente.turnos.size() == 1
            assert turno.paciente != null
    }

    void "H2.2 - Paciente reserva un turno de la misma especializacion el mismo mes da error"() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        String fechaDeNacimientoStr = "16/08/2002";
        LocalDate fechaDeNacimiento = LocalDate.parse(fechaDeNacimientoStr, dateFormatter);

        String fechaDelTurnoStr = "16/08/2023 14:00:00";
        LocalDateTime fechaDelTurno = LocalDateTime.parse(fechaDelTurnoStr, dateTimeFormatter);

        given: "Dado un paciente, un medico y un turno, e intento reservar un turno de la misma especialidad en el mismo mes"
            Paciente paciente = new Paciente("Juan", "Perez", "39805131", "juanPerez@gmail.com", fechaDeNacimiento)
            Medico medico = new Medico("Maria", "Gonzalez", "36456612", "Clinico", "M12873BD")
            Turno turno = medico.crearTurno(fechaDelTurno, "Hospital Italiano", 30)
            paciente.reservarTurno(turno)

        when: "cuando reservo un turno"
            paciente.reservarTurno(turno)

        then: "veo el turno reservado"
            thrown(ReservaDeTurnosException)
    }

    void "H2.3 - Paciente reserva un turno de la misma especializacion con mas de un mes de diferencia hacia atras"() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        String fechaDeNacimientoStr = "16/08/2002";
        LocalDate fechaDeNacimiento = LocalDate.parse(fechaDeNacimientoStr, dateFormatter);

        String fechaDelTurnoStr = "16/08/2023 14:00:00";
        LocalDateTime fechaDelTurno = LocalDateTime.parse(fechaDelTurnoStr, dateTimeFormatter);

        String fechaDelTurnoDelMesAnteriorStr = "16/07/2023 14:00:00";
        LocalDateTime fechaDelTurnoDelMesAnterior = LocalDateTime.parse(fechaDelTurnoDelMesAnteriorStr, dateTimeFormatter);

        given: "Dado un paciente, un medico y un turno, e intento reservar un turno de la misma especialidad en el mismo mes"
            Paciente paciente = new Paciente("Juan", "Perez", "39805131", "juanPerez@gmail.com", fechaDeNacimiento)
            Medico medico = new Medico("Maria", "Gonzalez", "36456612", "Clinico", "M12873BD")
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

    void "H2.4 - Paciente reserva un turno de la misma especializacion con menos de mes de diferencia hacia atras"() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        String fechaDeNacimientoStr = "16/08/2002";
        LocalDate fechaDeNacimiento = LocalDate.parse(fechaDeNacimientoStr, dateFormatter);

        String fechaDelTurnoStr = "16/08/2023 14:00:00";
        LocalDateTime fechaDelTurno = LocalDateTime.parse(fechaDelTurnoStr, dateTimeFormatter);

        String fechaDelTurnoDelMesAnteriorStr = "17/07/2023 14:00:00";
        LocalDateTime fechaDelTurnoDelMesAnterior = LocalDateTime.parse(fechaDelTurnoDelMesAnteriorStr, dateTimeFormatter);

        given: "Dado un paciente, un medico y un turno, e intento reservar un turno de la misma especialidad en el mismo mes"
            Paciente paciente = new Paciente("Juan", "Perez", "39805131", "juanPerez@gmail.com", fechaDeNacimiento)
            Medico medico = new Medico("Maria", "Gonzalez", "36456612", "Clinico", "M12873BD")
            Turno turno = medico.crearTurno(fechaDelTurno, "Hospital Italiano", 30)
            Turno turnoDelMesAnterior = medico.crearTurno(fechaDelTurnoDelMesAnterior, "Hospital Italiano", 30)
            paciente.reservarTurno(turno)

        when: "cuando reservo un turno"
            paciente.reservarTurno(turnoDelMesAnterior)

        then: "veo el turno reservado"
            thrown(ReservaDeTurnosException)
    }

    void "H2.5 - Paciente reserva un turno de la misma especializacion con menos de mes de diferencia hacia adelante"() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        String fechaDeNacimientoStr = "16/08/2002";
        LocalDate fechaDeNacimiento = LocalDate.parse(fechaDeNacimientoStr, dateFormatter);

        String fechaDelTurnoStr = "16/08/2023 14:00:00";
        LocalDateTime fechaDelTurno = LocalDateTime.parse(fechaDelTurnoStr, dateTimeFormatter);

        String fechaDelTurnoDelMesSiguienteStr = "15/09/2023 14:00:00";
        LocalDateTime fechaDelTurnoDelMesSiguiente = LocalDateTime.parse(fechaDelTurnoDelMesSiguienteStr, dateTimeFormatter);

        given: "Dado un paciente, un medico y un turno, e intento reservar un turno de la misma especialidad en el mismo mes"
        Paciente paciente = new Paciente("Juan", "Perez", "39805131", "juanPerez@gmail.com", fechaDeNacimiento)
        Medico medico = new Medico("Maria", "Gonzalez", "36456612", "Clinico", "M12873BD")
        Turno turno = medico.crearTurno(fechaDelTurno, "Hospital Italiano", 30)
        Turno turnoDelMesSiguiente = medico.crearTurno(fechaDelTurnoDelMesSiguiente, "Hospital Italiano", 30)
        paciente.reservarTurno(turno)

        when: "cuando reservo un turno"
            paciente.reservarTurno(turnoDelMesSiguiente)

        then: "veo el turno reservado"
            thrown(ReservaDeTurnosException)
    }


    void "H2.6 - Paciente reserva un turno de la misma especializacion con mas de mes de diferencia hacia adelante"() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        String fechaDeNacimientoStr = "16/08/2002";
        LocalDate fechaDeNacimiento = LocalDate.parse(fechaDeNacimientoStr, dateFormatter);

        String fechaDelTurnoStr = "16/08/2023 14:00:00";
        LocalDateTime fechaDelTurno = LocalDateTime.parse(fechaDelTurnoStr, dateTimeFormatter);

        String fechaDelTurnoDelMesSiguienteStr = "17/09/2023 14:00:00";
        LocalDateTime fechaDelTurnoDelMesSiguiente = LocalDateTime.parse(fechaDelTurnoDelMesSiguienteStr, dateTimeFormatter);

        given: "Dado un paciente, un medico y un turno, e intento reservar un turno de la misma especialidad en el mismo mes"
            Paciente paciente = new Paciente("Juan", "Perez", "39805131", "juanPerez@gmail.com", fechaDeNacimiento)
            Medico medico = new Medico("Maria", "Gonzalez", "36456612", "Clinico", "M12873BD")
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
