package no.nav.medlemskap.domene.ektefelle

import no.nav.medlemskap.domene.personhistorikk.Adresse
import no.nav.medlemskap.domene.personhistorikk.Statsborgerskap

data class PersonhistorikkEktefelle(
    val ident: String,
    val statsborgerskap: List<Statsborgerskap>,
    val barn: List<String>?,
    val bostedsadresser: List<Adresse>,
    val kontaktadresser: List<Adresse>,
    val oppholdsadresser: List<Adresse>
)
