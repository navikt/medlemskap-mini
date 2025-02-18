package no.nav.medlemskap.cucumber.steps.aareg.eregBuilder

import no.nav.medlemskap.clients.ereg.Bruksperiode
import java.time.LocalDate

class EregBruksperiodeBuilder {
    var tom = LocalDate.MIN
    var fom = LocalDate.MAX

    fun build(): Bruksperiode =
        Bruksperiode(
            fom = fom,
            tom = tom
        )
}
