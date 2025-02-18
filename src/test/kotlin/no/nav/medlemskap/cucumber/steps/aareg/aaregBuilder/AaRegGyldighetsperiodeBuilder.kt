package no.nav.medlemskap.cucumber.steps.aareg.aaregBuilder

import no.nav.medlemskap.clients.aareg.AaRegGyldighetsperiode
import java.time.LocalDate

class AaRegGyldighetsperiodeBuilder {
    var fom = LocalDate.MIN
    var tom = LocalDate.MAX

    fun build(): AaRegGyldighetsperiode =
        AaRegGyldighetsperiode(
            fom = fom,
            tom = tom
        )
}
