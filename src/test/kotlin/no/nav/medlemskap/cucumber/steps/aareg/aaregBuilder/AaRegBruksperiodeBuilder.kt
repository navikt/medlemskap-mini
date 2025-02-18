package no.nav.medlemskap.cucumber.steps.aareg.aaregBuilder

import no.nav.medlemskap.clients.aareg.AaRegBruksperiode
import java.time.LocalDateTime

class AaRegBruksperiodeBuilder {
    var fom = LocalDateTime.MIN
    var tom = LocalDateTime.MAX

    fun build(): AaRegBruksperiode =
        AaRegBruksperiode(
            fom = fom,
            tom = tom
        )
}
