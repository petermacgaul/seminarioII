package turnosMedicos

class UrlMappings {

    static mappings = {
        "/medico"(controller: 'medico')
        "/paciente"(controller: 'paciente')
        "/medico/turnos"(controller: 'turno')
        "/paciente/turnos"(controller: 'turnoPaciente')
        "/estudio"(controller: 'estudio')


        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/home")
        "/app-info"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
