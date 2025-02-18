package no.nav.medlemskap.cucumber.steps.aareg.eregBuilder

import no.nav.medlemskap.clients.ereg.Epostadresse

class EpostadresseBuilder {
    var bruksperiodeBuilder = EregBruksperiodeBuilder()
    var gyldighetsperiodeBuilder = EregGyldighetsperiodeBuilder()

    var bruksperiode = bruksperiodeBuilder.build()
    var gyldighetsperiode = gyldighetsperiodeBuilder.build()
    var adresse = String()

    fun build(): Epostadresse =
        Epostadresse(
            adresse = String(),
            bruksperiode = bruksperiode,
            gyldighetsperiode = gyldighetsperiode
        )
}
