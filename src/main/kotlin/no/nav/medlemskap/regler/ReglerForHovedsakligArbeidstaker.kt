package no.nav.medlemskap.regler

import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.regler.common.RegelId
import no.nav.medlemskap.regler.common.RegelId.REGEL_HOVEDSAKLIG_ARBEIDSTAKER
import no.nav.medlemskap.regler.common.Regelflyt
import no.nav.medlemskap.regler.common.Regelflyt.Companion.konklusjonUavklart
import no.nav.medlemskap.regler.common.Regelflyt.Companion.regelflytJa
import no.nav.medlemskap.regler.common.Regler
import no.nav.medlemskap.regler.common.Svar
import no.nav.medlemskap.regler.v1.RegelFactory

class ReglerForHovedsakligArbeidstaker(
    ytelse: Ytelse,
    regelFactory: RegelFactory,
    overstyrteRegler: Map<RegelId, Svar>
) : Regler(ytelse, regelFactory, overstyrteRegler) {
    override fun hentHovedflyt(): Regelflyt {
        val minst60ProsentStillingRegelFlyt = lagRegelflyt(
            regel = hentRegel(RegelId.REGEL_34),
            hvisJa = regelflytJa(ytelse, REGEL_HOVEDSAKLIG_ARBEIDSTAKER),
            hvisNei = konklusjonUavklart(ytelse, REGEL_HOVEDSAKLIG_ARBEIDSTAKER)
        )

        return minst60ProsentStillingRegelFlyt
    }

    companion object {
        fun fraDatagrunnlag(datagrunnlag: Datagrunnlag, overstyrteRegler: Map<RegelId, Svar> = emptyMap()): ReglerForHovedsakligArbeidstaker {
            return ReglerForHovedsakligArbeidstaker(
                ytelse = datagrunnlag.ytelse,
                regelFactory = RegelFactory(datagrunnlag),
                overstyrteRegler = overstyrteRegler
            )
        }
    }
}
