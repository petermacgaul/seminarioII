package turnosMedicos

class BootStrap {


    def init = { servletContext ->
        createCoberturas()
    }

    private void createCoberturas() {

        Cobertura oschot = new Oschot()
        oschot.save()
        Cobertura particular = new Particular()
        particular.save()
        Cobertura osdo = new Osdo()
        osdo.save()
    }

    def destroy = {
    }
}
