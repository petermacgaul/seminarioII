package turnosMedicos

class Oschot extends Cobertura{
    @Override
    Double calcularPrecioTurno(Double precioBase) {
        return precioBase * 0.2
    }
}
