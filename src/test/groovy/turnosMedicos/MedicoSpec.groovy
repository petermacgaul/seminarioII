package turnosMedicos

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MedicoSpec extends Specification implements DomainUnitTest<Medico> {

    def setup() {
    }

    def cleanup() {
    }
    void "Creacion de un medico"() {

        given: "Dado que me Maria Gonzalez, dni 36456612 y tengo una especialidad en Clinica con matricula M12873BD"

        when: "creo un paciente"
            Medico medico = new Medico("Maria", "Gonzalez", "36456612", "Clinico", "M12873BD")

        then: "veo su email"
            assert medico.dni == "36456612"
            assert medico.matricula == "M12873BD"
    }

}
