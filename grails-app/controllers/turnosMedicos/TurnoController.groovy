package turnosMedicos

import grails.validation.ValidationException

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import static org.springframework.http.HttpStatus.*

class TurnoController {

    TurnoService turnoService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", crearTurno: "POST", reservarTurno: "PUT"]

    DateTimeFormatter dateTimeFormatter;

    def index(Integer max, Integer id) {
        if (id == null){
            params.max = Math.min(max ?: 10, 100)
            respond turnoService.list(params), model:[turnoCount: turnoService.count()]
        } else {
            Medico medico = Medico.get(id)
            def turnoList = Turno.findAllByMedico(medico)
            respond(turnoList: turnoList, medico: medico)
        }
    }

    def show(Long id) {
        respond turnoService.get(id)
    }

    def create() {
        respond new Turno(params)
    }

    def save(Turno turno) {
        if (turno == null) {
            notFound()
            return
        }

        try {
            turnoService.save(turno)
        } catch (ValidationException e) {
            respond turno.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'turno.label', default: 'Turno'), turno.id])
                redirect turno
            }
            '*' { respond turno, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond turnoService.get(id)
    }

    def update(Turno turno) {
        if (turno == null) {
            notFound()
            return
        }

        try {
            turnoService.save(turno)
        } catch (ValidationException e) {
            respond turno.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'turno.label', default: 'Turno'), turno.id])
                redirect turno
            }
            '*'{ respond turno, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        turnoService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'turno.label', default: 'Turno'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
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
