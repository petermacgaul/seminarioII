package turnosMedicos

import grails.gorm.services.Service

@Service(Cobertura)
interface CoberturaService {

    Cobertura get(Serializable id)

    List<Cobertura> list(Map args)

    Long count()

    void delete(Serializable id)

    Cobertura save(Cobertura cobertura)

}