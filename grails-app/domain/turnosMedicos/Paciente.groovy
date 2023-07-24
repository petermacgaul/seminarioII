package turnosMedicos

import java.time.LocalDate
import java.time.LocalDateTime

class Paciente {

    String nombre
    String apellido
    String dni
    String email
    LocalDate fechaDeNacimiento
    Set<Turno> turnos = []
    Cobertura cobertura

    static hasMany = [ Turno ]

    static constraints = {
        nombre blank: false, nullable: false
        apellido blank: false, nullable: false
        dni blank: false, nullable: false
        email email: true, nullable: false, blank: false
        fechaDeNacimiento nullable: true
    }


    Paciente(String nombre, String apellido, String dni, String email, LocalDate fechaDeNacimiento, Cobertura cobertura) {
        if (nombre == null) throw new PacienteCreacionException("El nombre es incorrecto")
        if (apellido == null) throw new PacienteCreacionException("El apellido es incorrecto")
        if (dni == null) throw new PacienteCreacionException("El dni es incorrecto")
        if (email == null) throw new PacienteCreacionException("El email es incorrecto")
        if (fechaDeNacimiento == null) throw new PacienteCreacionException("El fechaDeNacimiento es incorrecto")

        this.nombre = nombre
        this.apellido = apellido
        this.dni = dni
        this.email = email
        this.fechaDeNacimiento = fechaDeNacimiento
        this.cobertura = cobertura
    }

    Paciente(String nombre, String apellido, String dni, String email, LocalDate fechaDeNacimiento) {
        if (nombre == null) throw new PacienteCreacionException("El nombre es incorrecto")
        if (apellido == null) throw new PacienteCreacionException("El apellido es incorrecto")
        if (dni == null) throw new PacienteCreacionException("El dni es incorrecto")
        if (email == null) throw new PacienteCreacionException("El email es incorrecto")
        if (fechaDeNacimiento == null) throw new PacienteCreacionException("El fechaDeNacimiento es incorrecto")

        this.nombre = nombre
        this.apellido = apellido
        this.dni = dni
        this.email = email
        this.fechaDeNacimiento = fechaDeNacimiento
        this.cobertura = new Particular()
    }
    Turno reservarTurno(Turno turno) {

        if (!turno.estaDisponible()){
            throw new TurnoNoDisponibleException();
        }

        boolean tengoTurnoDeMismaEspecialidadElMismoMes = turnos.any { Turno turnoReservado ->
            turnoReservado.medico.especialidad == turno.medico.especialidad
        }
        if (tengoTurnoDeMismaEspecialidadElMismoMes) throw new TurnoMismaEspecializacionException()

        boolean pacienteEstaBloqueado = turno.pacienteEstaBloqueado(this);
        if (pacienteEstaBloqueado) throw new PacienteBloqueadoException()

        turno.paciente = this
        turnos << turno
        turno
    }

    void cancelarTurno(Turno turno, LocalDateTime diaDeHoy = null) {
        if (!diaDeHoy){
            diaDeHoy = LocalDateTime.now()
        }
        turno.cancelar();

        boolean turnoCanceladoEnMenosDe72Horas = turno.fechaYHora < diaDeHoy.plusDays(3)

        if (turnoCanceladoEnMenosDe72Horas){
            turno.bloquearPaciente(this)
        }

        turnos.remove(turno);
    }

    Double obtenerPrecioTurno(Turno turno){
        return turno.calcularPrecio(cobertura)
    }

    void realizarEstudio(Turno turno, Estudio estudio){
        turno.estudios << estudio
    }
}
