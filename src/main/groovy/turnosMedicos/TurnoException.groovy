package turnosMedicos

import groovy.transform.InheritConstructors

@InheritConstructors
class ReservaDeTurnosException extends RuntimeException {
}

@InheritConstructors
class TurnoNoDisponibleException extends ReservaDeTurnosException {
    @Override
    String getMessage() {
        return "No se encuentra disponible"
    }
}

@InheritConstructors
class TurnoMismaEspecializacionException extends ReservaDeTurnosException {
    @Override
    String getMessage() {
        return "Tiene un turno de la misma especilidad ya reservado"
    }
}

@InheritConstructors
class CancelarTurnoException extends RuntimeException {
    @Override
    String getMessage() {
        return "No se puede cancelar el turno"
    }
}