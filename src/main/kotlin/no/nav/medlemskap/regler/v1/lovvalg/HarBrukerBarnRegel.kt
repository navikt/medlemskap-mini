package no.nav.medlemskap.regler.v1.lovvalg

import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.domene.barn.DataOmBarn
import no.nav.medlemskap.regler.common.Funksjoner.erIkkeTom
import no.nav.medlemskap.regler.common.RegelId
import no.nav.medlemskap.regler.common.Resultat
import no.nav.medlemskap.regler.common.Resultat.Companion.ja
import no.nav.medlemskap.regler.common.Resultat.Companion.nei
import java.time.LocalDate

class HarBrukerBarnRegel(
    ytelse: Ytelse,
    startDatoForYtelse: LocalDate,
    private val dataOmBarn: List<DataOmBarn>?,
    regelId: RegelId = RegelId.REGEL_11_2_1
) : LovvalgRegel(regelId, ytelse, startDatoForYtelse) {

    override fun operasjon(): Resultat {
        val barn = dataOmBarn

        return when {
            barn.erIkkeTom() -> ja(regelId)
            else -> nei(regelId)
        }
    }

    companion object {

        fun fraDatagrunnlag(datagrunnlag: Datagrunnlag, regelId: RegelId): HarBrukerBarnRegel {
            return HarBrukerBarnRegel(
                ytelse = datagrunnlag.ytelse,
                startDatoForYtelse = datagrunnlag.startDatoForYtelse,
                dataOmBarn = datagrunnlag.dataOmBarn,
                regelId = regelId
            )
        }
    }
}
