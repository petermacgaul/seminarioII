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

        Cobertura particular = new Particular()
        particular.save()

        Cobertura oschot = new Oschot()
        oschot.save()

        Cobertura osdo = new Osdo()
        osdo.save()
    }

    private void createPaciente() {

        Paciente paciente = new Paciente("Maria", "Perez", "39805131", "juanPerez@gmail.com", LocalDate.now().minusYears(20))
        paciente.save()
    }

    private void createMedico() {

        Medico medico = new Medico("Juan", "Gomez", "36456612", "Clinico", "M12873BD")
        medico.save()
    }

    private void createTurno() {

        Medico medico = Medico.get(1)

        Turno turno = medico.crearTurno(LocalDateTime.now().plusDays(1), "Hospital Italiano", 30)
        turno.save()

        Turno otroTurno = medico.crearTurno(LocalDateTime.now().plusDays(4), "Consultorio Privado", 30)
        otroTurno.save()
    }


    def destroy = {
    }
}
