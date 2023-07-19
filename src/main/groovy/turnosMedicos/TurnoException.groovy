package turnosMedicos

import groovy.transform.InheritConstructors

@InheritConstructors
class ReservaDeTurnosException extends RuntimeException {
}

@InheritConstructors
class TurnoNoDisponibleException extends ReservaDeTurnosException {
}

@InheritConstructors
class TurnoMismaEspecializacionException extends ReservaDeTurnosException {
}

@InheritConstructors
class CancelarTurnoException extends RuntimeException {
}