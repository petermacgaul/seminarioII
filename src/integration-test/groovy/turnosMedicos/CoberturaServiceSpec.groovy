package turnosMedicos

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class CoberturaServiceSpec extends Specification {

    CoberturaService coberturaService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Cobertura(...).save(flush: true, failOnError: true)
        //new Cobertura(...).save(flush: true, failOnError: true)
        //Cobertura cobertura = new Cobertura(...).save(flush: true, failOnError: true)
        //new Cobertura(...).save(flush: true, failOnError: true)
        //new Cobertura(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //cobertura.id
    }

    void "test get"() {
        setupData()

        expect:
        coberturaService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Cobertura> coberturaList = coberturaService.list(max: 2, offset: 2)

        then:
        coberturaList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        coberturaService.count() == 5
    }

    void "test delete"() {
        Long coberturaId = setupData()

        expect:
        coberturaService.count() == 5

        when:
        coberturaService.delete(coberturaId)
        sessionFactory.currentSession.flush()

        then:
        coberturaService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Cobertura cobertura = new Cobertura()
        coberturaService.save(cobertura)

        then:
        cobertura.id != null
    }
}
