package turnosMedicos

import java.time.LocalDateTime

class Medico {

    String nombre
    String apellido
    String dni
    String matricula
    String especialidad

    Set<Turno> turnos = []

    static hasMany = [
            turnos: Turno,
    ]

    static constraints = {
        nombre blank: false, nullable: false
        apellido blank: false, nullable: false
        dni nullable: false, blank: false
        matricula nullable: false, blank: false
        especialidad nullable: true
    }

    Medico(String nombre, String apellido, String dni, String especialidad, String matricula) {

        if (nombre == null) throw new MedicoCreacionException()
        if (apellido == null) throw new MedicoCreacionException()
        if (dni == null) throw new MedicoCreacionException()
        if (especialidad == null) throw new MedicoCreacionException()
        if (matricula == null) throw new MedicoCreacionException()

        this.nombre = nombre
        this.apellido = apellido
        this.dni = dni
        this.especialidad = especialidad
        this.matricula = matricula
    }

    Turno crearTurno(LocalDateTime fechaYHora, String lugar, Integer duracionEnMinutos) {
        Turno turno = new Turno(this, fechaYHora, lugar, duracionEnMinutos)
        turnos << turno
        turno
    }

    boolean pacienteEstaBloqueado(Paciente paciente) {
        return turnos.any { Turno turno ->
            turno.pacienteEstaEnListaDeBloqueados(paciente)
        }
    }
}
