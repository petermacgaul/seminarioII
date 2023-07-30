package turnosMedicos

class UrlMappings {

    static mappings = {
        "/medico"(controller: 'medico')
        "/paciente"(controller: 'paciente')
        "/turnos"(controller: 'turno')


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
