package no.nav.medlemskap.regler.v1.lovvalg

import no.nav.medlemskap.domene.Kontrollperiode.Companion.kontrollPeriodeForArbeidsforhold
import no.nav.medlemskap.domene.Kontrollperiode.Companion.kontrollPeriodeForPersonhistorikk
import no.nav.medlemskap.domene.Landkode
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.domene.personhistorikk.Adresse
import no.nav.medlemskap.domene.personhistorikk.Statsborgerskap
import no.nav.medlemskap.domene.personhistorikk.Statsborgerskap.Companion.erSveitsiskBorger
import no.nav.medlemskap.domene.personhistorikk.Statsborgerskap.Companion.gyldigeStatsborgerskap
import no.nav.medlemskap.regler.common.BasisRegel
import no.nav.medlemskap.regler.common.Funksjoner.alleEr
import no.nav.medlemskap.regler.common.Funksjoner.erIkkeTom
import no.nav.medlemskap.regler.common.Funksjoner.erTom
import no.nav.medlemskap.regler.common.RegelId
import java.time.LocalDate

abstract class LovvalgRegel(
    regelId: RegelId,
    ytelse: Ytelse,
    val startDatoForYtelse: LocalDate
) : BasisRegel(regelId, ytelse) {
    val kontrollPeriodeForArbeidsforhold = kontrollPeriodeForArbeidsforhold(startDatoForYtelse)
    val kontrollPeriodeForPersonhistorikk = kontrollPeriodeForPersonhistorikk(startDatoForYtelse)

    protected fun erPersonBosattINorge(
        boadadresse: List<Adresse>,
        postadresseLandkoder: List<String>,
        midlertidigAdresseLandkoder: List<String>
    ): Boolean {
        return boadadresse.brukerHarNorskBostedsadresse() && boadadresse.alleErNorske() &&
            personHarIngenEllerNorskPostadresse(postadresseLandkoder) &&
            personHarIngenEllerNorskMidlertidigadresse(midlertidigAdresseLandkoder)
    }

    protected fun List<Adresse>.alleErNorske(): Boolean {
        return this.map { it.landkode } alleEr "NOR"
    }

    protected fun List<Adresse>.brukerHarNorskBostedsadresse(): Boolean {
        return this.erIkkeTom()
    }

    protected fun personHarIngenEllerNorskPostadresse(postadresseLandkoder: List<String>): Boolean {
        return postadresseLandkoder.all { Landkode.erNorsk(it) } || postadresseLandkoder.erTom()
    }

    protected fun personHarIngenEllerNorskMidlertidigadresse(midlertidigadresserLandkoder: List<String>): Boolean {
        return midlertidigadresserLandkoder.all { Landkode.erNorsk(it) } || midlertidigadresserLandkoder.erTom()
    }

    protected fun erBrukerSveitsiskBorgerUtenAnnetEøsStatsborgerskap(statsborgerskap: List<Statsborgerskap>): Boolean {
        if (!statsborgerskap.erSveitsiskBorger(kontrollPeriodeForPersonhistorikk)) {
            return false
        }

        return !statsborgerskap
            .gyldigeStatsborgerskap(kontrollPeriodeForPersonhistorikk)
            .filterNot { Landkode.erSveitsisk(it) }.any { Landkode.erEØSland(it) }
    }
}
