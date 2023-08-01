package turnosMedicos


import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Turno {

    final static DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    Medico medico
    Paciente paciente

    LocalDateTime fecha

    String lugar
    Integer duracionEnMinutos
    Double precio
    Set<Paciente> pacientesBloqueados = []
    List<Estudio> estudios = []

    static belongsTo = [medico: Medico, paciente: Paciente]
    static mappedBy = [ estudios: "turno" ]

    static hasMany = [
            pacientesBloqueados: Paciente,
            estudios: Estudio,
    ]

    static constraints = {
        fecha nullable: false
        medico nullable: false
        paciente nullable: true
        lugar nullable: false, blank: false
        duracionEnMinutos nullable: false, blank: false, min: 0, max: 240
        precio nullable:false, blank:false, min:0D
    }


    Turno(Medico medico, LocalDateTime fecha, String lugar, Integer duracionEnMinutos) {
        if (medico == null) throw new TurnoCreacionException("medico no puede ser vacio")
        if (fecha == null) throw new TurnoCreacionException("fecha no puede ser vacio")
        if (fecha < LocalDateTime.now()) throw new TurnoCreacionException("La fecha no puede ser anterior al dia de hoy")
        if (lugar == null) throw new TurnoCreacionException("lugar no puede ser vacio")
        if (duracionEnMinutos == null) throw new TurnoCreacionException("duracionEnMinutos no puede ser vacio")

        // ToDo: validar reglas de negocio que afecten al paciente y al medico

        // Fechas mayores al dia de hoy
        // Duracion positiva
        // Duracion maxima

        this.medico = medico
        this.fecha = fecha
        this.lugar = lugar
        this.duracionEnMinutos = duracionEnMinutos
        this.precio = 10000
    }

    Boolean estaDisponible() {
        this.paciente == null
    }

    Boolean pacienteEstaBloqueado(Paciente paciente) {
        this.medico.pacienteEstaBloqueado(paciente, this.fecha)
    }

    Boolean pacienteEstaEnListaDeBloqueados(Paciente paciente, LocalDateTime diaDeHoy) {
        return diaDeHoy.month == this.fecha.month && pacientesBloqueados.any { Paciente pacienteBloqueado ->
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
        pacientesBloqueados.add(paciente)
    }

    Double calcularPrecio(Cobertura cobertura = null){
        Double precioTotal = this.precioTotal()

        if (paciente){
            return paciente.cobertura.calcularPrecioTurno(precioTotal)
        }

        if (cobertura){
            return cobertura.calcularPrecioTurno(precioTotal)
        }

        return precioTotal
    }

    Double precioTotal(){
        Double precioEstudios = calcularPrecioEstudios()
        return precio + precioEstudios
    }

    private Double calcularPrecioEstudios(){
        Double precioEstudios = 0D
        estudios.forEach(estudio -> precioEstudios = precioEstudios + estudio.precio)
        return precioEstudios
    }

    @Override
    String toString() {

        if (this.id == null) {
            return ''
        }

        return medico.toString() + " " + fecha.format(CUSTOM_FORMATTER)
    }

}
