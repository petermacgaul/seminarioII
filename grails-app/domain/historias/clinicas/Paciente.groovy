package historias.clinicas

class Paciente {

    String nombre
    String apellido
    Long dni
    String email

    static constraints = {
        nombre blank: false, nullable: false
        apellido blank: false, nullable: false
        dni nullable: false
        email email: true, nullable: false, blank: false
    }

}
