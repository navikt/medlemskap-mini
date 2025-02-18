package no.nav.medlemskap.cucumber.steps.aareg.eregBuilder

import no.nav.medlemskap.clients.ereg.Hjemlandregistre

class HjemlandRegistreBuilder {
    var bruksperiodeBuilder = EregBruksperiodeBuilder()
    var gyldighetsperiodeBuilder = EregGyldighetsperiodeBuilder()
    var postadresseBuilder = PostadresseBuilder()

    var bruksperiode = bruksperiodeBuilder.build()
    var gyldighetsperiode = gyldighetsperiodeBuilder.build()
    var postadresse = postadresseBuilder.build()
    var navn1 = String()
    var navn2 = String()
    var navn3 = String()
    var registernummer = String()

    fun build(): Hjemlandregistre =
        Hjemlandregistre(
            bruksperiode = bruksperiode,
            gyldighetsperiode = gyldighetsperiode,
            navn1 = navn1,
            navn2 = navn2,
            navn3 = navn3,
            postAdresse = postadresse,
            registernummer = registernummer
        )
}
