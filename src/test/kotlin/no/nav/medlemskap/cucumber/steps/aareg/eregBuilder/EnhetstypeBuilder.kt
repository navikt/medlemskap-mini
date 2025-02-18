package no.nav.medlemskap.cucumber.steps.aareg.eregBuilder

import no.nav.medlemskap.clients.ereg.Enhetstyper

class EnhetstypeBuilder {
    var bruksperiodeBuilder = EregBruksperiodeBuilder()
    var gyldighetsperiodeBuilder = EregGyldighetsperiodeBuilder()

    var bruksperiode = bruksperiodeBuilder.build()
    var gyldighetsperiode = gyldighetsperiodeBuilder.build()
    var enhetstype = String()

    fun build(): Enhetstyper =
        Enhetstyper(
            enhetstype = enhetstype,
            bruksperiode = bruksperiode,
            gyldighetsperiode = gyldighetsperiode
        )
}
