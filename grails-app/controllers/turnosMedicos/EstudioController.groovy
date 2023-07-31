package turnosMedicos

import grails.validation.ValidationException

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND

class EstudioController {

    EstudioService estudioService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def show(Long id) {
        respond estudioService.get(id)
    }

    def create() {
        respond new Estudio(params)
    }

    def save(Estudio estudio) {
        if (estudio == null) {
            notFound()
            return
        }

        try {
            estudioService.save(estudio)
        } catch (ValidationException e) {
            respond estudio.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'estudio.label', default: 'Estudio'), estudio.id])
                redirect estudio
            }
            '*' { respond estudio, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond estudioService.get(id)
    }

    def index(Integer turnoId) {
        Turno turno = Turno.get(turnoId)
        def estudioList = Estudio.findAllByTurno(turno)
        respond(estudioList: estudioList, turno: turno)
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'medico.label', default: 'Medico'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
