package no.nav.medlemskap.cucumber.steps.aareg.eregBuilder

import no.nav.medlemskap.clients.ereg.MVA

class MVABuilder {
    var bruksperiodeBuilder = EregBruksperiodeBuilder()
    var gyldighetsperiodeBuilder = EregGyldighetsperiodeBuilder()

    var registrertIMVA = String()
    var bruksperiode = bruksperiodeBuilder.build()
    var gyldighetsperiode = gyldighetsperiodeBuilder.build()

    fun build(): MVA =
        MVA(
            registrertIMVA = registrertIMVA,
            bruksperiode = bruksperiode,
            gyldighetsperiode = gyldighetsperiode
        )
}
