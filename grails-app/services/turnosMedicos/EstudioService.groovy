package turnosMedicos

import grails.gorm.services.Service

@Service(Estudio)
interface EstudioService {

    Estudio get(Serializable id)

    List<Estudio> list(Map args)

    Long count()

    void delete(Serializable id)

    Estudio save(Estudio estudio)
}
