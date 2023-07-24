package turnosMedicos

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class CoberturaController {

    CoberturaService coberturaService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond coberturaService.list(params), model:[coberturaCount: coberturaService.count()]
    }

    def show(Long id) {
        respond coberturaService.get(id)
    }

    def edit(Long id) {
        respond coberturaService.get(id)
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        coberturaService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'cobertura.label', default: 'Cobertura'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'cobertura.label', default: 'Cobertura'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
