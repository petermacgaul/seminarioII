package turnosMedicos

import grails.gorm.services.Service

@Service(Turno)
interface TurnoService {

    Turno get(Serializable id)

    List<Turno> list(Map args)

    Long count()

    void delete(Serializable id)

    Turno save(Turno turno)

}