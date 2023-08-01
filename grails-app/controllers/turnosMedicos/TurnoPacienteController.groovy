package turnosMedicos

import grails.validation.ValidationException
import org.hibernate.proxy.LazyInitializer

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

class TurnoPacienteController {

    TurnoService turnoService
    PacienteService pacienteService

    static allowedMethods = [obtenerCosto: "GET", cancelarTurno: "POST", reservarTurno: "POST"]

    DateTimeFormatter dateTimeFormatter;


    def reservarTurno() {

        Paciente paciente = Paciente.get(params.pacienteId)
        Turno turno = Turno.get(params.turnoId)

        try {

            paciente.reservarTurno(turno)
            paciente.addToTurnos(turno)

            turnoService.save(turno)
            pacienteService.save(paciente)

            flash.message = "Turno Reservado!"

        } catch (ReservaDeTurnosException exception ) {
            flash.error = "${exception.message} al reservar el turno ${turno}."
        } catch (PacienteBloqueadoException exception ) {
            flash.error = "Usted esta bloqueado para reservar el turno con ${turno}."
        } finally {
            redirect(action: 'index', id: params.pacienteId)
        }
    }

    def index(Integer max, Integer id) {
        Paciente paciente = Paciente.get(id)
        paciente.cobertura = paciente.cobertura.refresh()

        def turnoList = Turno.findAllByPacienteIsNullOrPaciente(paciente)
        turnoList.collect {
            paciente.obtenerPrecioTurno(it)
            it
        }

        respond(turnoList: turnoList, paciente: paciente)
    }

    def show(Long id) {
        respond turnoService.get(id)
    }

    def cancelarTurno() {
        Paciente paciente = Paciente.get(params.pacienteId)
        Turno turno = Turno.get(params.turnoId)

        try {

            boolean pacienteBloqueado = paciente.elTurnoCanceladoEnMenosDe72Horas(turno);

            paciente.cancelarTurno(turno)
            paciente.removeFromTurnos(turno)
            turno.removeFrom('paciente', paciente)

            pacienteService.save(paciente)
            turnoService.save(turno)

            if (pacienteBloqueado){
                flash.warning = "Turno Cancelado en menos de 72 Hs. No podra sacar turno con el doctor durante este mes"
            } else {
                flash.message = "Turno Cancelado!"
            }

        } catch (CancelarTurnoException exception ) {
            flash.error = "Error ${exception} al cancelar el turno ${turno}."
        } finally {
            redirect(action: 'index', id: params.pacienteId)
        }
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
