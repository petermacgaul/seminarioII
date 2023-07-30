package turnosMedicos

abstract class Cobertura {

    abstract Double calcularPrecioTurno(Double precioBase)

    @Override
    String toString() {
        return this.getClass().simpleName
    }

}