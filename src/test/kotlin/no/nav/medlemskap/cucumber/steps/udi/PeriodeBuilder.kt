package no.nav.medlemskap.cucumber.steps.udi

import no.nav.medlemskap.domene.Periode
import java.time.LocalDate

class PeriodeBuilder {
    var fom: LocalDate = LocalDate.now()
    var tom: LocalDate = LocalDate.now()

    fun build(): Periode {
        return Periode(fom, tom)
    }
}
