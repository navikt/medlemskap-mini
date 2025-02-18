package no.nav.medlemskap.cucumber.steps.aareg.eregBuilder

import no.nav.medlemskap.clients.ereg.Gyldighetsperiode
import java.time.LocalDate

class EregGyldighetsperiodeBuilder {

    var fom = LocalDate.MIN
    var tom = LocalDate.MAX

    fun build(): Gyldighetsperiode =
        Gyldighetsperiode(
            fom = fom,
            tom = tom
        )
}
