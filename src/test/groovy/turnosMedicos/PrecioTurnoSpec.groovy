package turnosMedicos

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PrecioTurnoSpec extends Specification implements DomainUnitTest<Turno> {

    DateTimeFormatter dateFormatter
    DateTimeFormatter dateTimeFormatter
    LocalDate fechaDeNacimiento
    LocalDateTime fechaDelTurno
    LocalDateTime diaDeHoy

    Medico medico
    Paciente pacienteOSDO
    Paciente pacienteOSCHOT
    Paciente pacienteParticular
    Turno turno
    Estudio estudio

    def setup() {
        dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
        medico = new Medico("Juan", "Gomez", "36456612", "Traumatologo", "M12873BD")
        fechaDeNacimiento = LocalDate.parse("16/08/2002", dateFormatter)
        pacienteOSDO = new Paciente("Maria", "Perez", "39805131", "mariaPerez@gmail.com", fechaDeNacimiento, new Osdo())
        pacienteOSCHOT = new Paciente("Juana", "Perez", "39805133", "juanaPerez@gmail.com", fechaDeNacimiento, new Oschot())
        pacienteParticular = new Paciente("Sofia", "Perez", "39855131", "sofiaPerez@gmail.com", fechaDeNacimiento)

        fechaDelTurno = LocalDateTime.parse("16/08/2023 14:00:00", dateTimeFormatter)
        turno = medico.crearTurno(fechaDelTurno, "Hospital Italiano", 30)
        diaDeHoy = LocalDateTime.parse("13/08/2023 14:00:01", dateTimeFormatter)

    }

    def cleanup() {
    }

    void "US3.1 - Cálculo de precio de turno - Turno simple obra social OSDO"() {

        given: "Dado que soy un paciente y tengo obra social “OSDO” y el turno con un traumatólogo cuesta \$10000"
        when: "selecciono el un turno para consulta traumatológica"
        pacienteOSDO.reservarTurno(turno)

        then: "se me indica que la obra social cubre el costo del mismo"
        assert pacienteOSDO.obtenerPrecioTurno(turno) == 0D
    }

    void "US3.2 - Cálculo de precio de turno - Turno simple obra social OSCHOT"() {

        given: "Dado que soy un paciente y tengo obra social “OSCHOT” y el turno con un traumatólogo cuesta \$10000"
        when: "selecciono el un turno para consulta traumatológica"

        pacienteOSCHOT.reservarTurno(turno)

        then: "se me indica que la obra social cubre el 80% del costo debiendo abonar \$2000"
        assert pacienteOSCHOT.obtenerPrecioTurno(turno) == 2000
    }
    void "US3.3 - Cálculo de precio de turno - Turno simple particular"() {

        given: "Dado que soy un paciente y no tengo obra social y el turno con un traumatólogo cuesta \$10000"
        when: "selecciono el un turno para consulta traumatológica"

        pacienteParticular.reservarTurno(turno)

        then: "se me indica que el costo del turno es de \$10000"
        assert pacienteParticular.obtenerPrecioTurno(turno) == 10000
    }
    void "US3.4 - Cálculo de precio de turno - Turno con estudios particular"() {

        given: "Dado que soy un paciente y seleccione un turno de consulta con un traumatólogo que cuesta \$10000 y el traumatólogo agrega dos estudios con un costo de \$5000 cada uno "
        pacienteParticular.reservarTurno(turno)
        medico.solicitarEstudio(turno, estudio)
        estudio = new Estudio(pacienteParticular, "Rayos")
        medico.solicitarEstudio(turno, estudio)
        medico.solicitarEstudio(turno, estudio)

        when: "consulto el precio del turno"
        then: "se me indica que el costo del turno es de \$20000"
        assert pacienteParticular.obtenerPrecioTurno(turno) == 20000
    }
    void "US3.5 - Cálculo de precio de turno - Turno con estudios obra social OSDO"() {

        given: "Dado que soy un paciente y tengo obra social “OSDO” y seleccione un turno de consulta con un traumatólogo que cuesta \$10000 y el traumatólogo agrega dos estudios con un costo de \$5000 cada uno "
        pacienteOSDO.reservarTurno(turno)

        estudio = new Estudio(pacienteOSDO, "Rayos")
        medico.solicitarEstudio(turno, estudio)
        medico.solicitarEstudio(turno, estudio)

        when: "consulto el precio del turno"
        then: " se me indica que la obra social cubre el costo del mismo"
        assert pacienteOSDO.obtenerPrecioTurno(turno) == 0D
    }
    void "US3.6 - Cálculo de precio de turno - Turno con estudios obra social OSCHOT"() {

        given: "Dado que soy un paciente y tengo obra social “OSCHOT” y seleccione un turno de consulta con un traumatólogo que cuesta \$10000 y el traumatólogo agrega dos estudios con un costo de \$5000 cada uno "
        pacienteOSCHOT.reservarTurno(turno)

        estudio = new Estudio(pacienteOSCHOT, "Rayos")
        medico.solicitarEstudio(turno, estudio)
        medico.solicitarEstudio(turno, estudio)

        when: "consulto el precio del turno"
        then: "sse me indica que la obra social cubre el 80% del costo debiendo abonar \$4000"
        assert pacienteOSCHOT.obtenerPrecioTurno(turno) == 4000
    }

}
