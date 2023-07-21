package turnosMedicos

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PacienteSpec extends Specification implements DomainUnitTest<Paciente> {

    def setup() {
    }

    def cleanup() {
    }

    void "Creacion de un paciente"() {

        given: "Dado que naci el 16/08/2002 y me llamo Juan Perez, email: juanPerez@gmail.com con dni: 1"
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String fechaDeNacimientoStr = "16/08/2002";
            LocalDate fechaDeNacimiento = LocalDate.parse(fechaDeNacimientoStr, dateFormatter);

        when: "creo un paciente"
            Paciente paciente = new Paciente("Juan", "Perez", "1", "juanPerez@gmail.com", fechaDeNacimiento)

        then: "veo su email"
            assert paciente.email == "juanPerez@gmail.com"
    }
}
