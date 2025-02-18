package no.nav.medlemskap.cucumber.steps.aareg.eregBuilder

import no.nav.medlemskap.clients.ereg.Status

class StatusBuilder {
    var bruksperiodeBuilder = EregBruksperiodeBuilder()
    var gyldighetsperiodeBuilder = EregGyldighetsperiodeBuilder()

    var kode = String()
    var bruksperiode = bruksperiodeBuilder.build()
    var gyldighetsperiode = gyldighetsperiodeBuilder.build()
    fun build(): Status =
        Status(
            kode = kode,
            bruksperiode = bruksperiode,
            gyldighetsperiode = gyldighetsperiode
        )
}
