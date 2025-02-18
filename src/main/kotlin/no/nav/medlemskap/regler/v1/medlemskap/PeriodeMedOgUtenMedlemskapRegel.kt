package no.nav.medlemskap.regler.v1.medlemskap

import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.Medlemskap
import no.nav.medlemskap.domene.Medlemskap.Companion.harMedlPeriodeMedOgUtenMedlemskap
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.regler.common.RegelId
import no.nav.medlemskap.regler.common.Resultat
import no.nav.medlemskap.regler.common.Resultat.Companion.ja
import no.nav.medlemskap.regler.common.Resultat.Companion.nei
import java.time.LocalDate

class PeriodeMedOgUtenMedlemskapRegel(
    ytelse: Ytelse,
    startDatoForYtelse: LocalDate,
    private val medlemskap: List<Medlemskap>
) : MedlemskapRegel(RegelId.REGEL_1_2, ytelse, startDatoForYtelse, medlemskap) {

    override fun operasjon(): Resultat {
        return when {
            medlemskap harMedlPeriodeMedOgUtenMedlemskap kontrollPeriodeForMedl -> ja(regelId)
            else -> nei(regelId)
        }
    }

    companion object {

        fun fraDatagrunnlag(datagrunnlag: Datagrunnlag): PeriodeMedOgUtenMedlemskapRegel {
            return PeriodeMedOgUtenMedlemskapRegel(
                ytelse = datagrunnlag.ytelse,
                startDatoForYtelse = datagrunnlag.startDatoForYtelse,
                medlemskap = datagrunnlag.medlemskap
            )
        }
    }
}
