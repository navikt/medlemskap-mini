package no.nav.medlemskap.cucumber.steps.aareg.eregBuilder

import no.nav.medlemskap.clients.ereg.Formaal

class FormaalBuilder {
    var bruksperiodeBuilder = EregBruksperiodeBuilder()
    var gyldighetsperiodeBuilder = EregGyldighetsperiodeBuilder()

    var bruksperiode = bruksperiodeBuilder.build()
    var gyldighetsperiode = gyldighetsperiodeBuilder.build()
    var formaal = String()

    fun build(): Formaal =
        Formaal(
            bruksperiode = bruksperiode,
            formaal = formaal,
            gyldighetsperiode = gyldighetsperiode
        )
}
