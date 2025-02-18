package no.nav.medlemskap.regler.v1.statsborgerskap

import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.domene.personhistorikk.Statsborgerskap
import no.nav.medlemskap.domene.personhistorikk.Statsborgerskap.Companion.erBritiskBorger
import no.nav.medlemskap.regler.common.RegelId
import no.nav.medlemskap.regler.common.Resultat
import no.nav.medlemskap.regler.v1.lovvalg.LovvalgRegel
import java.time.LocalDate

class ErBrukerBritiskBorgerRegel(
    regelId: RegelId = RegelId.REGEL_19_7,
    ytelse: Ytelse,
    private val statsborgerskap: List<Statsborgerskap>,
    startDatoForYtelse: LocalDate
) : LovvalgRegel(regelId, ytelse, startDatoForYtelse) {

    override fun operasjon(): Resultat {

        return when {
            statsborgerskap.erBritiskBorger(kontrollPeriodeForPersonhistorikk) -> Resultat.ja(regelId)
            else -> Resultat.nei(regelId)
        }
    }

    companion object {
        fun fraDatagrunnlag(datagrunnlag: Datagrunnlag): ErBrukerBritiskBorgerRegel {

            return ErBrukerBritiskBorgerRegel(
                regelId = RegelId.REGEL_19_7,
                ytelse = datagrunnlag.ytelse,
                statsborgerskap = datagrunnlag.pdlpersonhistorikk.statsborgerskap,
                startDatoForYtelse = datagrunnlag.startDatoForYtelse
            )
        }
    }
}
