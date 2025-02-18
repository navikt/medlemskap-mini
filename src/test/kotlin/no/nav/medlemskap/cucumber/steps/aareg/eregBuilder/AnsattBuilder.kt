package no.nav.medlemskap.cucumber.steps.aareg.eregBuilder

import no.nav.medlemskap.clients.ereg.Ansatte

class AnsattBuilder {
    var gyldighetsperiodeBuilder = EregGyldighetsperiodeBuilder()
    var bruksperiodeBuilder = EregBruksperiodeBuilder()

    var antall = 1
    var gyldighetsperiode = gyldighetsperiodeBuilder.build()
    var bruksperiode = bruksperiodeBuilder.build()

    fun build(): Ansatte =
        Ansatte(
            antall = antall,
            gyldighetsperiode = gyldighetsperiode,
            bruksperiode = bruksperiode
        )
}
