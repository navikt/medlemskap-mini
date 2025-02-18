package no.nav.medlemskap.regler.v1

import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.InputPeriode
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.regler.common.RegelId
import no.nav.medlemskap.regler.common.RegelId.REGEL_12
import no.nav.medlemskap.regler.common.RegelId.REGEL_NORSK
import no.nav.medlemskap.regler.common.Regelflyt
import no.nav.medlemskap.regler.common.Regelflyt.Companion.regelflytJa
import no.nav.medlemskap.regler.common.Regelflyt.Companion.regelflytUavklart
import no.nav.medlemskap.regler.common.Regler
import no.nav.medlemskap.regler.common.Svar

class ReglerForNorskeStatsborgere(
    val periode: InputPeriode,
    ytelse: Ytelse,
    regelFactory: RegelFactory,
    overstyrteRegler: Map<RegelId, Svar>
) : Regler(ytelse, regelFactory, overstyrteRegler) {

    override fun hentHovedflyt(): Regelflyt {

        val harBrukerJobbet25ProsentEllerMerFlyt = lagRegelflyt(
            regel = hentRegel(REGEL_12),
            hvisJa = regelflytJa(ytelse, REGEL_NORSK),
            hvisNei = regelflytUavklart(ytelse, REGEL_NORSK)
        )
        val harBrukerJobbetUtenforNorgeFlyt = lagRegelflyt(
            regel = hentRegel(RegelId.REGEL_9),
            hvisJa = regelflytUavklart(ytelse, REGEL_NORSK),
            hvisNei = harBrukerJobbet25ProsentEllerMerFlyt
        )

        return harBrukerJobbetUtenforNorgeFlyt
    }

    companion object {
        fun fraDatagrunnlag(datagrunnlag: Datagrunnlag, overstyrteRegler: Map<RegelId, Svar> = emptyMap()): ReglerForNorskeStatsborgere {

            return ReglerForNorskeStatsborgere(
                periode = datagrunnlag.periode,
                ytelse = datagrunnlag.ytelse,
                regelFactory = RegelFactory(datagrunnlag),
                overstyrteRegler = overstyrteRegler
            )
        }
    }
}
