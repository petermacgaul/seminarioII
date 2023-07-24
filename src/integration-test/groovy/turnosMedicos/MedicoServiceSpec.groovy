package turnosMedicos

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class MedicoServiceSpec extends Specification {

    MedicoService medicoService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Medico(...).save(flush: true, failOnError: true)
        //new Medico(...).save(flush: true, failOnError: true)
        //Medico medico = new Medico(...).save(flush: true, failOnError: true)
        //new Medico(...).save(flush: true, failOnError: true)
        //new Medico(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //medico.id
    }

    void "test get"() {
        setupData()

        expect:
        medicoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Medico> medicoList = medicoService.list(max: 2, offset: 2)

        then:
        medicoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        medicoService.count() == 5
    }

    void "test delete"() {
        Long medicoId = setupData()

        expect:
        medicoService.count() == 5

        when:
        medicoService.delete(medicoId)
        sessionFactory.currentSession.flush()

        then:
        medicoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Medico medico = new Medico()
        medicoService.save(medico)

        then:
        medico.id != null
    }
}
