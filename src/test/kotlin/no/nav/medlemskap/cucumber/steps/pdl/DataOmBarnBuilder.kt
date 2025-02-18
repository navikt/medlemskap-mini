package no.nav.medlemskap.cucumber.steps.pdl

import no.nav.medlemskap.domene.barn.DataOmBarn
import no.nav.medlemskap.domene.barn.PersonhistorikkBarn
import no.nav.medlemskap.domene.personhistorikk.Adresse
import no.nav.medlemskap.domene.personhistorikk.ForelderBarnRelasjon

class DataOmBarnBuilder {
    var personhistorikkBarn = PersonhistorikkBarn(
        ident = String(),
        bostedsadresser = mutableListOf<Adresse>(),
        kontaktadresser = mutableListOf<Adresse>(),
        oppholdsadresser = mutableListOf<Adresse>(),
        forelderBarnRelasjon = mutableListOf<ForelderBarnRelasjon>()

    )

    fun build(): DataOmBarn {
        return DataOmBarn(
            personhistorikkBarn = personhistorikkBarn
        )
    }
}
