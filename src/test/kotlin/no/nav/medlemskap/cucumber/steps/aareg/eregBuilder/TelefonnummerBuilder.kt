package no.nav.medlemskap.cucumber.steps.aareg.eregBuilder

import no.nav.medlemskap.clients.ereg.Telefonnummer

class TelefonnummerBuilder {
    var bruksperiodeBuilder = EregBruksperiodeBuilder()
    var gyldighetsperiodeBuilder = EregGyldighetsperiodeBuilder()

    var bruksperiode = bruksperiodeBuilder.build()
    var gyldighetsperiode = gyldighetsperiodeBuilder.build()
    var nummer = String()
    var telefontype = String()

    fun build(): Telefonnummer =
        Telefonnummer(
            nummer = nummer,
            bruksperiode = bruksperiode,
            gyldighetsperiode = gyldighetsperiode,
            Telefontype = telefontype
        )
}
