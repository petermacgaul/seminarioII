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

    static allowedMethods = [obtenerCosto: "GET", cancelarTurno: "POST", reservarTurno: "POST"]

    DateTimeFormatter dateTimeFormatter;


    def reservarTurno() {

        Paciente paciente = Paciente.get(params.pacienteId)
        Turno turno = Turno.get(params.turnoId)

        try {

            paciente.reservarTurno(turno)

            turno.save(failOnError: true)
            paciente.save(failOnError: true)

            flash.message = "Turno Reservado!"

        } catch (ReservaDeTurnosException exception ) {
            flash.error = "Error ${exception} al reservar el turno ${turno}."
        } finally {
            redirect(action: 'index', id: params.pacienteId)
        }
    }

    def index(Integer max, Integer id) {
        Paciente paciente = Paciente.get(id)
        def turnoList = Turno.findAllByPacienteIsNullOrPaciente(paciente)
        respond(turnoList: turnoList, paciente: paciente)
    }

    def show(Long id) {
        respond turnoService.get(id)
    }

    def cancelarTurno() {
        Paciente paciente = Paciente.get(params.pacienteId)
        Turno turno = Turno.get(params.turnoId)

        try {

            paciente.cancelarTurno(turno)

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
