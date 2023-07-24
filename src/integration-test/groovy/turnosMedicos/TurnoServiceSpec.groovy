package turnosMedicos

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class TurnoServiceSpec extends Specification {

    TurnoService turnoService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Turno(...).save(flush: true, failOnError: true)
        //new Turno(...).save(flush: true, failOnError: true)
        //Turno turno = new Turno(...).save(flush: true, failOnError: true)
        //new Turno(...).save(flush: true, failOnError: true)
        //new Turno(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //turno.id
    }

    void "test get"() {
        setupData()

        expect:
        turnoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Turno> turnoList = turnoService.list(max: 2, offset: 2)

        then:
        turnoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        turnoService.count() == 5
    }

    void "test delete"() {
        Long turnoId = setupData()

        expect:
        turnoService.count() == 5

        when:
        turnoService.delete(turnoId)
        sessionFactory.currentSession.flush()

        then:
        turnoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Turno turno = new Turno()
        turnoService.save(turno)

        then:
        turno.id != null
    }
}
