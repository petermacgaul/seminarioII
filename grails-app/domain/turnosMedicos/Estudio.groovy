package turnosMedicos

class Estudio {
    Turno turno
    Paciente paciente
    String tipo
    Double precio

    static belongsTo = [turno: Turno]

    @Override
    String toString() {
        return this.getClass().simpleName + " " + tipo
    }

    static constraints = {
        turno blank: false, nullable: false
        paciente blank: false, nullable: false
        tipo blank: false, nullable: false
        precio nullable: false, blank: false, min: 0D
    }

    Estudio(Turno turno, String tipo){
        this.turno = turno
        this.paciente = turno.paciente
        this.tipo = tipo
        this.precio = 5000
    }
}
