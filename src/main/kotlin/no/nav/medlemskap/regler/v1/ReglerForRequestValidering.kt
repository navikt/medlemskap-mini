package no.nav.medlemskap.regler.v1

import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.InputPeriode
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.regler.common.*
import no.nav.medlemskap.regler.common.Regelflyt.Companion.regelflytJa
import no.nav.medlemskap.regler.common.Regelflyt.Companion.regelflytUavklart

class ReglerForRequestValidering(
    val periode: InputPeriode,
    ytelse: Ytelse,
    regelFactory: RegelFactory,
    overstyrteRegler: Map<RegelId, Svar>
) : Regler(ytelse, regelFactory, overstyrteRegler) {

    override fun hentHovedflyt(): Regelflyt {

        return lagRegelflyt(
            regel = hentRegel(RegelId.REGEL_0_1),
            hvisJa = regelflytJa(ytelse, RegelId.REGEL_REQUEST_VALIDERING),
            hvisNei = regelflytUavklart(ytelse, RegelId.REGEL_REQUEST_VALIDERING)
        )
    }

    fun kjørRegel(): Resultat {
        return hentHovedflyt().utfør()
    }

    companion object {
        fun fraDatagrunnlag(datagrunnlag: Datagrunnlag): ReglerForRequestValidering {
            with(datagrunnlag) {
                return ReglerForRequestValidering(
                    periode = periode,
                    ytelse = ytelse,
                    regelFactory = RegelFactory(datagrunnlag),
                    overstyrteRegler = datagrunnlag.overstyrteRegler
                )
            }
        }
    }
}
