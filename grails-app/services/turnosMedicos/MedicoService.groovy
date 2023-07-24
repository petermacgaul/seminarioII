package turnosMedicos

import grails.gorm.services.Service

@Service(Medico)
interface MedicoService {

    Medico get(Serializable id)

    List<Medico> list(Map args)

    Long count()

    void delete(Serializable id)

    Medico save(Medico medico)

}