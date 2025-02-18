package no.nav.medlemskap.regler.v1

import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.InputPeriode
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.regler.common.RegelId
import no.nav.medlemskap.regler.common.RegelId.*
import no.nav.medlemskap.regler.common.Regelflyt
import no.nav.medlemskap.regler.common.Regelflyt.Companion.regelflytJa
import no.nav.medlemskap.regler.common.Regelflyt.Companion.regelflytUavklart
import no.nav.medlemskap.regler.common.Regler
import no.nav.medlemskap.regler.common.Svar

class ReglerForAndreStatsborgere(
    val periode: InputPeriode,
    ytelse: Ytelse,
    regelFactory: RegelFactory,
    overstyrteRegler: Map<RegelId, Svar>
) : Regler(ytelse, regelFactory, overstyrteRegler) {

    override fun hentHovedflyt(): Regelflyt {
        val arbeidUtenforNorgeRegelflyt = lagRegelflyt(
            regel = hentRegel(REGEL_9),
            hvisJa = regelflytUavklart(ytelse, REGEL_ANDRE_BORGERE),
            hvisNei = regelflytJa(ytelse, REGEL_ANDRE_BORGERE)
        )

        val harBrukerJobbet80ProsentEllerMerSiste3Månedeneflyt = lagRegelflyt(
            regel = hentRegel(REGEL_20),
            hvisJa = arbeidUtenforNorgeRegelflyt,
            hvisNei = regelflytUavklart(ytelse, REGEL_ANDRE_BORGERE)
        )

        val erBrukerBosattINorgeRegelflyt = lagRegelflyt(
            regel = hentRegel(REGEL_10),
            hvisJa = arbeidUtenforNorgeRegelflyt,
            hvisNei = harBrukerJobbet80ProsentEllerMerSiste3Månedeneflyt
        )

        return erBrukerBosattINorgeRegelflyt
    }

    companion object {
        fun fraDatagrunnlag(datagrunnlag: Datagrunnlag): ReglerForAndreStatsborgere {
            with(datagrunnlag) {
                return ReglerForAndreStatsborgere(
                    periode = periode,
                    ytelse = ytelse,
                    regelFactory = RegelFactory(datagrunnlag),
                    overstyrteRegler = overstyrteRegler
                )
            }
        }
    }
}
