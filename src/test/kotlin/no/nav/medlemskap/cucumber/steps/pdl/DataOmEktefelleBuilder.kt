package no.nav.medlemskap.cucumber.steps.pdl

import no.nav.medlemskap.domene.arbeidsforhold.Arbeidsforhold
import no.nav.medlemskap.domene.ektefelle.DataOmEktefelle
import no.nav.medlemskap.domene.ektefelle.PersonhistorikkEktefelle
import no.nav.medlemskap.domene.personhistorikk.Adresse
import no.nav.medlemskap.domene.personhistorikk.Statsborgerskap

class DataOmEktefelleBuilder {
    var personhistorikkEktefelle = PersonhistorikkEktefelle(
        ident = String(),
        barn = mutableListOf<String>(),
        statsborgerskap = mutableListOf<Statsborgerskap>(),
        bostedsadresser = mutableListOf<Adresse>(),
        kontaktadresser = mutableListOf<Adresse>(),
        oppholdsadresser = mutableListOf<Adresse>()

    )
    var arbeidsforholdEktefelle = listOf<Arbeidsforhold>()

    fun build(): DataOmEktefelle {
        return DataOmEktefelle(
            personhistorikkEktefelle,
            arbeidsforholdEktefelle
        )
    }
}
