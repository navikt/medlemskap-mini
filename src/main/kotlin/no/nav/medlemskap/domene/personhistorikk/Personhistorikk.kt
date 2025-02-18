package no.nav.medlemskap.domene.personhistorikk

import no.nav.medlemskap.domene.InputPeriode
import no.nav.medlemskap.regler.common.Funksjoner.isNotNullOrEmpty
import java.time.LocalDate

data class Personhistorikk(
    val statsborgerskap: List<Statsborgerskap>,
    val bostedsadresser: List<Adresse>,
    val kontaktadresser: List<Adresse>,
    val oppholdsadresser: List<Adresse>,
    val sivilstand: List<Sivilstand>,
    val forelderBarnRelasjon: List<ForelderBarnRelasjon>,
    val doedsfall: List<LocalDate>,
    val innflyttingTilNorge: List<InnflyttingTilNorge>,
    val utflyttingFraNorge: List<UtflyttingFraNorge>,
    val navn: List<Navn>,
    val oppholdstilatelser: List<Opphold> = emptyList()
) {

    fun erBrukerDoed(): Boolean {
        return doedsfall.isNotNullOrEmpty()
    }

    fun erBrukerDoedEtterPeriode(inputPeriode: InputPeriode): Boolean {
        return erBrukerDoed() && doedsfall.all { it.isAfter(inputPeriode.tom) }
    }

    fun harInnflyttingTilNorge(): Boolean {
        return innflyttingTilNorge.isNotNullOrEmpty()
    }

    fun harUtflyttingFraNorge(): Boolean {
        return utflyttingFraNorge.isNotNullOrEmpty()
    }
}
