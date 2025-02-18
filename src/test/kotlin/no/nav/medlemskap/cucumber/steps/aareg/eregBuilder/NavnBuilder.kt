package no.nav.medlemskap.cucumber.steps.aareg.eregBuilder

import no.nav.medlemskap.clients.ereg.Navn

class NavnBuilder {
    var bruksperiodeBuilder = EregBruksperiodeBuilder()
    var gyldighetsperiodeBuilder = EregGyldighetsperiodeBuilder()

    var bruksperiode = bruksperiodeBuilder.build()
    var gyldighetsperiode = gyldighetsperiodeBuilder.build()
    var navnelinje1 = String()
    var navnelinje2 = String()
    var navnelinje3 = String()
    var navnelinje4 = String()
    var navnelinje5 = String()
    var redigertNavn = String()

    fun build(): Navn =
        Navn(
            gyldighetsperiode = gyldighetsperiode,
            bruksperiode = bruksperiode,
            navnelinje1 = navnelinje1,
            navnelinje2 = navnelinje2,
            navnelinje3 = navnelinje3,
            navnelinje4 = navnelinje4,
            navnelinje5 = navnelinje5,
            redigertnavn = redigertNavn
        )
}
