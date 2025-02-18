package no.nav.medlemskap.regler.v1.grunnforordningen

import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.domene.personhistorikk.Statsborgerskap
import no.nav.medlemskap.domene.personhistorikk.Statsborgerskap.Companion.erEøsBorger
import no.nav.medlemskap.regler.common.RegelId
import no.nav.medlemskap.regler.common.Resultat
import no.nav.medlemskap.regler.common.Resultat.Companion.ja
import no.nav.medlemskap.regler.common.Resultat.Companion.nei
import no.nav.medlemskap.regler.v1.lovvalg.LovvalgRegel
import java.time.LocalDate

class ErBrukerEøsBorgerRegel(
    ytelse: Ytelse,
    startDatoForYtelse: LocalDate,
    val statsborgerskap: List<Statsborgerskap>
) : LovvalgRegel(RegelId.REGEL_2, ytelse, startDatoForYtelse) {

    override fun operasjon(): Resultat {
        return if (statsborgerskap.erEøsBorger(kontrollPeriodeForPersonhistorikk)) {
            ja(regelId)
        } else {
            nei(regelId)
        }
    }

    companion object {
        fun fraDatagrunnlag(datagrunnlag: Datagrunnlag): ErBrukerEøsBorgerRegel {
            return ErBrukerEøsBorgerRegel(
                ytelse = datagrunnlag.ytelse,
                startDatoForYtelse = datagrunnlag.startDatoForYtelse,
                statsborgerskap = datagrunnlag.pdlpersonhistorikk.statsborgerskap
            )
        }
    }
}
