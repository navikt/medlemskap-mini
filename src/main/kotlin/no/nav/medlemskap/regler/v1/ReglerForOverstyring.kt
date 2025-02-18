package no.nav.medlemskap.regler.v1

import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.InputPeriode
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.regler.common.*
import no.nav.medlemskap.regler.common.Regelflyt.Companion.regelflytJa
import no.nav.medlemskap.regler.v1.overstyring.OverstyringRegel

class ReglerForOverstyring(
    val periode: InputPeriode,
    ytelse: Ytelse,
    regelFactory: RegelFactory,
    overstyrteRegler: Map<RegelId, Svar>
) : Regler(ytelse, regelFactory, overstyrteRegler) {

    override fun hentHovedflyt(): Regelflyt {
        return lagRegelflyt(
            regel = hentRegel(RegelId.REGEL_0_5),
            hvisJa = regelflytJa(ytelse, RegelId.REGEL_OVERSTYRING),
            hvisNei = regelflytJa(ytelse, RegelId.REGEL_OVERSTYRING)
        )
    }

    fun reglerSomSkalOverstyres(resultat: Resultat): Map<RegelId, Svar> {
        val overstyringResultat = resultat.delresultat.find { it.regelId == RegelId.REGEL_0_5 }

        if (overstyringResultat?.svar == Svar.JA) {
            return OverstyringRegel.reglerSomSkalOverstyres()
        } else {
            return overstyrteRegler
        }
    }

    companion object {
        fun fraDatagrunnlag(datagrunnlag: Datagrunnlag): ReglerForOverstyring {
            with(datagrunnlag) {
                return ReglerForOverstyring(
                    periode = periode,
                    ytelse = ytelse,
                    regelFactory = RegelFactory(datagrunnlag),
                    overstyrteRegler = datagrunnlag.overstyrteRegler
                )
            }
        }
    }
}
