package no.nav.medlemskap.cucumber.steps.pdl

import no.nav.medlemskap.domene.ektefelle.PersonhistorikkEktefelle
import no.nav.medlemskap.domene.personhistorikk.Adresse
import no.nav.medlemskap.domene.personhistorikk.Statsborgerskap

class PersonhistorikkEktefelleBuilder {

    var ident = String()
    var barn = mutableListOf<String>()
    val statsborgerskap = mutableListOf<Statsborgerskap>()
    val bostedsadresse = mutableListOf<Adresse>()
    val kontaktadresse = mutableListOf<Adresse>()
    val oppholdsadresse = mutableListOf<Adresse>()

    fun build(): PersonhistorikkEktefelle {
        return PersonhistorikkEktefelle(
            ident = ident,
            barn = barn,
            statsborgerskap = statsborgerskap,
            bostedsadresser = bostedsadresse,
            kontaktadresser = kontaktadresse,
            oppholdsadresser = oppholdsadresse
        )
    }
}
