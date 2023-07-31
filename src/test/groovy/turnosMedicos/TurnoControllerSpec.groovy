package turnosMedicos

import grails.testing.gorm.DomainUnitTest
import grails.testing.web.controllers.ControllerUnitTest
import grails.validation.ValidationException
import spock.lang.*

class TurnoControllerSpec extends Specification implements ControllerUnitTest<TurnoController>, DomainUnitTest<Turno> {

    def populateValidParams(params) {
        assert params != null
    }

    void "Test the index action returns the correct model"() {
        given:
        controller.turnoService = Mock(TurnoService) {
            1 * list(_) >> []
            1 * count() >> 0
        }

        when:"The index action is executed"
        controller.index()

        then:"The model is correct"
        !model.turnoList
        model.turnoCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
        controller.create()

        then:"The model is correctly created"
        model.turno!= null
    }

    void "Test the save action with a null instance"() {
        when:"Save is called for a domain instance that doesn't exist"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'POST'
        controller.save(null)

        then:"A 404 error is returned"
        response.redirectedUrl == '/turno/index'
        flash.message != null
    }

    void "Test the save action correctly persists"() {
        given:
        controller.turnoService = Mock(TurnoService) {
            1 * save(_ as Turno)
        }

        when:"The save action is executed with a valid instance"
        response.reset()
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'POST'
        populateValidParams(params)
        def turno = new Turno(params)
        turno.id = 1

        controller.save(turno)

        then:"A redirect is issued to the show action"
        response.redirectedUrl == '/turno/show/1'
        controller.flash.message != null
    }

    void "Test the save action with an invalid instance"() {
        given:
        controller.turnoService = Mock(TurnoService) {
            1 * save(_ as Turno) >> { Turno turno ->
                throw new ValidationException("Invalid instance", turno.errors)
            }
        }

        when:"The save action is executed with an invalid instance"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'POST'
        def turno = new Turno()
        controller.save(turno)

        then:"The create view is rendered again with the correct model"
        model.turno != null
        view == 'create'
    }

    void "Test the show action with a null id"() {
        given:
        controller.turnoService = Mock(TurnoService) {
            1 * get(null) >> null
        }

        when:"The show action is executed with a null domain"
        controller.show(null)

        then:"A 404 error is returned"
        response.status == 404
    }

    void "Test the show action with a valid id"() {
        given:
        controller.turnoService = Mock(TurnoService) {
            1 * get(2) >> new Turno()
        }

        when:"A domain instance is passed to the show action"
        controller.show(2)

        then:"A model is populated containing the domain instance"
        model.turno instanceof Turno
    }

    void "Test the edit action with a null id"() {
        given:
        controller.turnoService = Mock(TurnoService) {
            1 * get(null) >> null
        }

        when:"The show action is executed with a null domain"
        controller.edit(null)

        then:"A 404 error is returned"
        response.status == 404
    }

    void "Test the edit action with a valid id"() {
        given:
        controller.turnoService = Mock(TurnoService) {
            1 * get(2) >> new Turno()
        }

        when:"A domain instance is passed to the show action"
        controller.edit(2)

        then:"A model is populated containing the domain instance"
        model.turno instanceof Turno
    }


    void "Test the update action with a null instance"() {
        when:"Save is called for a domain instance that doesn't exist"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'PUT'
        controller.update(null)

        then:"A 404 error is returned"
        response.redirectedUrl == '/turno/index'
        flash.message != null
    }

    void "Test the update action correctly persists"() {
        given:
        controller.turnoService = Mock(TurnoService) {
            1 * save(_ as Turno)
        }

        when:"The save action is executed with a valid instance"
        response.reset()
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'PUT'
        populateValidParams(params)
        def turno = new Turno(params)
        turno.id = 1

        controller.update(turno)

        then:"A redirect is issued to the show action"
        response.redirectedUrl == '/turno/show/1'
        controller.flash.message != null
    }

    void "Test the update action with an invalid instance"() {
        given:
        controller.turnoService = Mock(TurnoService) {
            1 * save(_ as Turno) >> { Turno turno ->
                throw new ValidationException("Invalid instance", turno.errors)
            }
        }

        when:"The save action is executed with an invalid instance"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'PUT'
        controller.update(new Turno())

        then:"The edit view is rendered again with the correct model"
        model.turno != null
        view == 'edit'
    }

    void "Test the delete action with a null instance"() {
        when:"The delete action is called for a null instance"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'DELETE'
        controller.delete(null)

        then:"A 404 is returned"
        response.redirectedUrl == '/turno/index'
        flash.message != null
    }

    void "Test the delete action with an instance"() {
        given:
        controller.turnoService = Mock(TurnoService) {
            1 * delete(2)
        }

        when:"The domain instance is passed to the delete action"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'DELETE'
        controller.delete(2)

        then:"The user is redirected to index"
        response.redirectedUrl == '/turno/index'
        flash.message != null
    }
}






