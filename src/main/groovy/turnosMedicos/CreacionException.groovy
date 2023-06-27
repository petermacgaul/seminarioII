package turnosMedicos

import groovy.transform.InheritConstructors

@InheritConstructors
class CreacionException extends RuntimeException {
}

@InheritConstructors
class TurnoCreacionException extends CreacionException {
}

@InheritConstructors
class PacienteCreacionException extends CreacionException {
}

@InheritConstructors
class MedicoCreacionException extends CreacionException {
}