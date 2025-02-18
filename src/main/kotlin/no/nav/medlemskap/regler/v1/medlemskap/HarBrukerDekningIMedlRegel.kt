package no.nav.medlemskap.regler.v1.medlemskap

import no.nav.medlemskap.common.dekningCounter
import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.Dekning
import no.nav.medlemskap.domene.Dekning.Companion.gjelderForYtelse
import no.nav.medlemskap.domene.Medlemskap
import no.nav.medlemskap.domene.Medlemskap.Companion.gjeldendeDekning
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.domene.Ytelse.Companion.name
import no.nav.medlemskap.regler.common.RegelId
import no.nav.medlemskap.regler.common.Resultat
import no.nav.medlemskap.regler.common.Resultat.Companion.ja
import no.nav.medlemskap.regler.common.Resultat.Companion.nei
import java.time.LocalDate

class HarBrukerDekningIMedlRegel(
    ytelse: Ytelse,
    startDatoForYtelse: LocalDate,
    private val medlemskap: List<Medlemskap>
) : MedlemskapRegel(RegelId.REGEL_1_7, ytelse, startDatoForYtelse, medlemskap) {

    override fun operasjon(): Resultat {
        val dekning = medlemskap gjeldendeDekning kontrollPeriodeForMedl

        dekningCounter(dekning!!, ytelse.name()) // Dekning er ikke null, regel 1.5 filtrerer ut dekning som er null

        return when {
            Dekning.from(dekning)!!.gjelderForYtelse(ytelse) -> ja(regelId = regelId, dekning = dekning)
            else -> nei(regelId = regelId, dekning = dekning)
        }
    }

    companion object {

        fun fraDatagrunnlag(datagrunnlag: Datagrunnlag): HarBrukerDekningIMedlRegel {
            return HarBrukerDekningIMedlRegel(
                ytelse = datagrunnlag.ytelse,
                medlemskap = datagrunnlag.medlemskap,
                startDatoForYtelse = datagrunnlag.startDatoForYtelse
            )
        }
    }
}
