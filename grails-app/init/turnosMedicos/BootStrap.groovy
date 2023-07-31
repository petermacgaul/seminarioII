package turnosMedicos

import java.time.LocalDate
import java.time.LocalDateTime

class BootStrap {


    def init = { servletContext ->
        createCoberturas()
        createPaciente()
        createMedico()
        createTurno()
    }

    private void createCoberturas() {

        Particular particular = new Particular()
        particular.save()

        Oschot oschot = new Oschot()
        oschot.save()

        Osdo osdo = new Osdo()
        osdo.save()
    }

    private void createPaciente() {

        Paciente paciente = new Paciente("Maria", "Perez", "39805131", "juanPerez@gmail.com", LocalDate.now().minusYears(20))
        paciente.save()

        Oschot cobertura = Cobertura.get(2)
        Paciente pacienteConObraSocial = new Paciente("Pablo", "Juarez", "14568123", "pabloJuarez@gmail.com", LocalDate.now().minusYears(22), cobertura)
        pacienteConObraSocial.save()
    }

    private void createMedico() {

        Medico medico = new Medico("Juan", "Gomez", "36456612", "Clinico", "M12873BD")
        medico.save()

        Medico medicoTraumatologo = new Medico("Sofia", "Jimenez", "26584123", "Traumatologo", "ASK456DF")
        medicoTraumatologo.save()

        Medico medicoObstetra = new Medico("Maria", "Gutierrez", "23554333", "Obstetra", "456DF151P")
        medicoObstetra.save()

    }

    private void createTurno() {

        Medico medico = Medico.get(1)
        Medico otroMedico = Medico.get(2)

        Turno turno = medico.crearTurno(LocalDateTime.now().plusDays(1), "Hospital Italiano", 30)
        turno.save()

        Turno otroTurno = medico.crearTurno(LocalDateTime.now().plusDays(4), "Consultorio Privado", 30)
        otroTurno.save()

        Turno otroTurnoConOtroMedico = otroMedico.crearTurno(LocalDateTime.now().plusDays(4), "Las Lomas", 30)
        otroTurnoConOtroMedico.save()
    }


    def destroy = {
    }
}
