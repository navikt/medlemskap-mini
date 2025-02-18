package no.nav.medlemskap.cucumber.steps.aareg.eregBuilder

import no.nav.medlemskap.clients.ereg.NAVSpesifikkInformasjon

class NavSpesifikkInformasjonBuilder {
    var bruksperiodeBuilder = EregBruksperiodeBuilder()
    var gyldighetsperiodeBuilder = EregGyldighetsperiodeBuilder()

    var bruksperiode = bruksperiodeBuilder.build()
    var gyldighetsperiode = gyldighetsperiodeBuilder.build()
    var erIA = true

    fun build(): NAVSpesifikkInformasjon =
        NAVSpesifikkInformasjon(
            bruksperiode = bruksperiode,
            erIA = erIA,
            gyldighetsperiode = gyldighetsperiode
        )
}
