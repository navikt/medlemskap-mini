package no.nav.medlemskap.regler.v1

import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.InputPeriode
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.regler.common.RegelId
import no.nav.medlemskap.regler.common.RegelId.*
import no.nav.medlemskap.regler.common.Regelflyt
import no.nav.medlemskap.regler.common.Regelflyt.Companion.regelflytJa
import no.nav.medlemskap.regler.common.Regler
import no.nav.medlemskap.regler.common.Svar

class ReglerForStatsborgerskap(
    val periode: InputPeriode,
    ytelse: Ytelse,
    regelFactory: RegelFactory,
    overstyrteRegler: Map<RegelId, Svar>
) : Regler(ytelse, regelFactory, overstyrteRegler) {

    override fun hentHovedflyt(): Regelflyt {
        val harBrukerNorskStatsborgerskapFlyt = lagRegelflyt(
            regel = hentRegel(REGEL_11),
            hvisJa = regelflytJa(ytelse, REGEL_STATSBORGERSKAP),
            hvisNei = regelflytJa(ytelse, REGEL_STATSBORGERSKAP)
        )

        val harBrukerNyligBlittNorskStatsborgerFlyt = lagRegelflyt(
            regel = hentRegel(REGEL_27),
            hvisJa = Regelflyt.regelflytUavklart(ytelse, REGEL_STATSBORGERSKAP),
            hvisNei = regelflytJa(ytelse, REGEL_STATSBORGERSKAP)
        )

        val erBrukerEØSborgerFlyt = lagRegelflyt(
            regel = hentRegel(REGEL_2),
            hvisJa = harBrukerNorskStatsborgerskapFlyt,
            hvisNei = harBrukerNyligBlittNorskStatsborgerFlyt
        )

        return erBrukerEØSborgerFlyt
    }

    companion object {
        fun fraDatagrunnlag(datagrunnlag: Datagrunnlag): ReglerForStatsborgerskap {
            with(datagrunnlag) {
                return ReglerForStatsborgerskap(
                    periode = periode,
                    ytelse = ytelse,
                    regelFactory = RegelFactory(datagrunnlag),
                    overstyrteRegler = datagrunnlag.overstyrteRegler
                )
            }
        }
    }
}
