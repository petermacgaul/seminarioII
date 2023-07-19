package turnosMedicos

import java.time.LocalDateTime

class Turno {

    Medico medico
    Paciente paciente

    LocalDateTime fechaYHora

    String lugar
    Integer duracionEnMinutos

    static constraints = {
        fechaYHora nullable: true
        medico nullable: false
        lugar nullable: false, blank: false
        duracionEnMinutos nullable: false, blank: false, min: 0, max: 240
    }

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

    void cancelar() {
        this.paciente = null;
    }
}
