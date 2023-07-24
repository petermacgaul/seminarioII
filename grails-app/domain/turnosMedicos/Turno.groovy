package turnosMedicos


import java.time.LocalDateTime

class Turno {

    Medico medico
    Paciente paciente

    LocalDateTime fechaYHora

    String lugar
    Integer duracionEnMinutos
    Double precio
    Set<Paciente> pacientesBloqueados = []
    List<Estudio> estudios = []

    static belongsTo = [Medico, Paciente]


    static hasMany = [
            pacientesBloqueados: Paciente,
            estudios: Estudio,
    ]

    static constraints = {
        fechaYHora nullable: true
        medico nullable: false
        lugar nullable: false, blank: false
        duracionEnMinutos nullable: false, blank: false, min: 0, max: 240
        precio nullable:false, blank:false, min:0D
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
        this.precio = 10000
    }

    Boolean estaDisponible() {
        this.paciente == null
    }

    Boolean pacienteEstaBloqueado(Paciente paciente) {
        this.medico.pacienteEstaBloqueado(paciente, this.fechaYHora)
    }

    Boolean pacienteEstaEnListaDeBloqueados(Paciente paciente, LocalDateTime diaDeHoy) {
        return diaDeHoy.month == this.fechaYHora.month && pacientesBloqueados.any { Paciente pacienteBloqueado ->
            pacienteBloqueado.dni == paciente.dni
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

    Double calcularPrecio(Cobertura cobertura){
        Double precioEstudios = calcularPrecioEstudios()
        return cobertura.calcularPrecioTurno(precio + precioEstudios)
    }

    private Double calcularPrecioEstudios(){
        Double precioEstudios = 0D
        estudios.forEach(estudio -> precioEstudios = precioEstudios + estudio.precio)
        return precioEstudios
    }

}
