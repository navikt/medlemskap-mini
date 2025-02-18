package no.nav.medlemskap.cucumber.steps.aareg.eregBuilder

import no.nav.medlemskap.clients.ereg.Postadresse

class PostadresseBuilder {
    var gyldighetsperiodeBuilder = EregGyldighetsperiodeBuilder()
    var bruksperiodeBuilder = EregBruksperiodeBuilder()

    var adresselinje1 = String()
    var adresselinje2 = String()
    var adresselinje3 = String()
    var gyldighetsperiode = gyldighetsperiodeBuilder.build()
    var bruksperiode = bruksperiodeBuilder.build()
    var kommunenr = String()
    var landkode = String()
    var poststed = String()
    var postnummer = String()

    fun build(): Postadresse =
        Postadresse(
            adresselinje1 = adresselinje1,
            adresselinje2 = adresselinje2,
            adresselinje3 = adresselinje3,
            gyldighetsperiode = gyldighetsperiode,
            bruksperiode = bruksperiode,
            kommunenr = kommunenr,
            landkode = landkode,
            poststed = poststed,
            postnummer = postnummer
        )
}
