package no.nav.medlemskap.cucumber.steps.aareg.eregBuilder

import no.nav.medlemskap.clients.ereg.Naering

class NaeringBuilder {
    var bruksperiodeBuilder = EregBruksperiodeBuilder()
    var gyldighetsperiodeBuilder = EregGyldighetsperiodeBuilder()

    var bruksperiode = bruksperiodeBuilder.build()
    var gyldighetsperiode = gyldighetsperiodeBuilder.build()
    var hjelpeenhet = true
    var naeringskode = String()

    fun build(): Naering =
        Naering(
            bruksperiode = bruksperiode,
            gyldighetsperiode = gyldighetsperiode,
            hjelpeenhet = hjelpeenhet,
            naeringskode = naeringskode
        )
}
