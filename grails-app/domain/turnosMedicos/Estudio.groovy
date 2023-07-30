package turnosMedicos

class Estudio {
    Paciente paciente
    String tipo
    Double precio

    @Override
    String toString() {
        return this.getClass().simpleName + " " + tipo
    }

    static constraints = {
        paciente blank: false, nullable: false
        tipo blank: false, nullable: false
        precio nullable: false, blank: false, min: 0D
    }

    Estudio(Paciente paciente, String tipo){
        this.paciente = paciente
        this.tipo = tipo
        this.precio = 5000
    }
}
