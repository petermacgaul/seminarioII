package turnosMedicos

import grails.validation.ValidationException

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

class TurnoPacienteController {

    TurnoService turnoService

    static allowedMethods = [obtenerCosto: "GET", cancelarTurno: "PUT", reservarTurno: "PUT"]

    DateTimeFormatter dateTimeFormatter;


    def reservarTurno() {

        Paciente paciente = Paciente.get(params.paciente)
        Turno turno = Turno.get(params.turno)

        try {

            turno.save(failOnError: true)
            paciente.save(failOnError: true)
            render "Paciente reserva el turno ${turno}"

        } catch (ReservaDeTurnosException exception ) {
            render "Error ${exception} al reservar el turno ${turno}."
        }
    }

    def index(Integer max, Integer id) {
        Paciente paciente = Paciente.get(id)
        respond Turno.findAllByPacienteIsNullOrPaciente(paciente)
    }

    def show(Long id) {
        respond turnoService.get(id)
    }

    def cancelarTurno() {
        Paciente paciente = Paciente.get(params.paciente)
        Turno turno = Turno.get(params.turno)
        paciente.cancelarTurno(turno)

        try {

            turno.save(failOnError: true)
            paciente.save(failOnError: true)
            render "Paciente cancela el turno ${turno}"

        } catch (ReservaDeTurnosException exception ) {
            render "Error ${exception} al cancelar el turno ${turno}."
        }
    }

    def obtenerCosto() {
        Paciente paciente = Paciente.get(params.paciente)
        Turno turno = Turno.get(params.turno)
        respond  paciente.obtenerPrecioTurno(turno)
    }

    protected void notFound() {
            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'turno.label', default: 'Turno'), params.id])
                    redirect action: "index", method: "GET"
                }
                '*'{ render status: NOT_FOUND }
            }
        }


}
