package no.nav.medlemskap.regler.v1.medlemskap

import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.Medlemskap
import no.nav.medlemskap.domene.Medlemskap.Companion.harPeriodeMedMedlemskap
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.regler.common.RegelId
import no.nav.medlemskap.regler.common.Resultat
import no.nav.medlemskap.regler.common.Resultat.Companion.ja
import no.nav.medlemskap.regler.common.Resultat.Companion.nei
import java.time.LocalDate

class PeriodeMedMedlemskapRegel(
    ytelse: Ytelse,
    startDatoForYtelse: LocalDate,
    private val medlemskap: List<Medlemskap>
) : MedlemskapRegel(RegelId.REGEL_1_3, ytelse, startDatoForYtelse, medlemskap) {

    override fun operasjon(): Resultat {
        return when {
            medlemskap harPeriodeMedMedlemskap kontrollPeriodeForMedl -> ja(regelId)
            else -> nei(regelId)
        }
    }

    companion object {

        fun fraDatagrunnlag(datagrunnlag: Datagrunnlag): PeriodeMedMedlemskapRegel {
            return PeriodeMedMedlemskapRegel(
                ytelse = datagrunnlag.ytelse,
                startDatoForYtelse = datagrunnlag.startDatoForYtelse,
                medlemskap = datagrunnlag.medlemskap
            )
        }
    }
}
