package no.nav.medlemskap.cucumber.steps.aareg.eregBuilder

import no.nav.medlemskap.clients.ereg.Internettadresse

class InternettadresseBuilder {
    var bruksperiodeBuilder = EregBruksperiodeBuilder()
    var gyldighetsperiodeBuilder = EregGyldighetsperiodeBuilder()

    var bruksperiode = bruksperiodeBuilder.build()
    var gyldighetsperiode = gyldighetsperiodeBuilder.build()
    var adresse = String()

    fun build(): Internettadresse =
        Internettadresse(
            adresse = adresse,
            bruksperiode = bruksperiode,
            gyldighetsperiode = gyldighetsperiode
        )
}
