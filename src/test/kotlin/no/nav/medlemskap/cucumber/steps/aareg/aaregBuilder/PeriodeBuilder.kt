package no.nav.medlemskap.cucumber.steps.aareg.aaregBuilder

import no.nav.medlemskap.clients.aareg.AaRegPeriode
import no.nav.medlemskap.domene.Periode
import java.time.LocalDate

class PeriodeBuilder {
    var fom = LocalDate.MIN
    var tom = LocalDate.MAX

    fun build(): Periode =
        Periode(
            fom = fom,
            tom = tom
        )

    fun buildAaregPeriode(): AaRegPeriode =
        AaRegPeriode(
            fom = fom,
            tom = tom
        )
}
