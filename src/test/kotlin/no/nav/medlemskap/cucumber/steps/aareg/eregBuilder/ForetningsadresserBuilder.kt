package no.nav.medlemskap.cucumber.steps.aareg.eregBuilder

import no.nav.medlemskap.clients.ereg.ForretningsAdresser

class ForetningsadresserBuilder {
    var bruksperiodeBuilder = EregBruksperiodeBuilder()
    var gyldighetsperiodeBuilder = EregGyldighetsperiodeBuilder()

    var adresselinje1 = String()
    var adresselinje2 = String()
    var adresselinje3 = String()
    var gyldighetsperiode = gyldighetsperiodeBuilder.build()
    var bruksperiode = bruksperiodeBuilder.build()
    var kommunenr = String()
    var landkode = String()
    var postnummer = String()
    var poststed = String()

    fun build(): ForretningsAdresser =
        ForretningsAdresser(
            adresselinje1 = adresselinje1,
            adresselinje2 = adresselinje2,
            adresselinje3 = adresselinje3,
            gyldighetsperiode = gyldighetsperiode,
            bruksperiode = bruksperiode,
            kommunenr = kommunenr,
            landkode = landkode,
            postnummer = postnummer,
            poststed = poststed
        )
}
