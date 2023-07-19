package turnosMedicos

import org.apache.tools.ant.taskdefs.Local

import java.time.LocalDateTime

class Turno {

    Medico medico
    Paciente paciente

    LocalDateTime fechaYHora

    String lugar
    Integer duracionEnMinutos
    Set<Paciente> pacientesBloqueados = []

    static constraints = {
        fechaYHora nullable: true
        medico nullable: false
        lugar nullable: false, blank: false
        duracionEnMinutos nullable: false, blank: false, min: 0, max: 240
    }

    static hasMany = [
            pacientesBloqueados: Paciente,
    ]

    Turno(Medico medico, LocalDateTime fechaYHora, String lugar, Integer duracionEnMinutos) {
        if (medico == null) throw new TurnoCreacionException("medico no puede ser vacio")
        if (fechaYHora == null) throw new TurnoCreacionException("fechaYHora no puede ser vacio")
        if (lugar == null) throw new TurnoCreacionException("lugar no puede ser vacio")
        if (duracionEnMinutos == null) throw new TurnoCreacionException("duracionEnMinutos no puede ser vacio")

        // ToDo: validar reglas de negocio que afecten al paciente y al medico

        // Fechas mayores al dia de hoy
        // Duracion positiva
        // Duracion maxima

        this.medico = medico
        this.fechaYHora = fechaYHora
        this.lugar = lugar
        this.duracionEnMinutos = duracionEnMinutos
    }

    Boolean estaDisponible() {
        this.paciente == null
    }

    Boolean pacienteEstaBloqueado(Paciente paciente) {
        this.medico.pacienteEstaBloqueado(paciente, this.fechaYHora)
    }

    Boolean pacienteEstaEnListaDeBloqueados(Paciente paciente, LocalDateTime diaDeHoy) {
        return diaDeHoy.month == this.fechaYHora.month && pacientesBloqueados.any { Paciente pacienteBloqueado ->
            pacienteBloqueado.dni == paciente.dni && pacienteBloqueado.id == paciente.id
        }
    }

    void cancelar() {
        if (this.paciente == null){
            throw new CancelarTurnoException();
        }
        this.paciente = null;
    }

    void bloquearPaciente(Paciente paciente) {
        pacientesBloqueados << paciente
    }
}
