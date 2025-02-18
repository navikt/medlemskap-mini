package no.nav.medlemskap.regler.v1.medlemskap

import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.Dekning
import no.nav.medlemskap.domene.Dekning.Companion.uavklartForYtelse
import no.nav.medlemskap.domene.Medlemskap
import no.nav.medlemskap.domene.Medlemskap.Companion.gjeldendeDekning
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.regler.common.RegelId
import no.nav.medlemskap.regler.common.Resultat
import no.nav.medlemskap.regler.common.Resultat.Companion.ja
import no.nav.medlemskap.regler.common.Resultat.Companion.nei
import java.time.LocalDate

class ErBrukersDekningUavklartRegel(
    ytelse: Ytelse,
    val medlemskap: List<Medlemskap>,
    startDatoForYtelse: LocalDate
) : MedlemskapRegel(RegelId.REGEL_1_6, ytelse, startDatoForYtelse, medlemskap) {

    override fun operasjon(): Resultat {
        val gjeldendeDekning = medlemskap.gjeldendeDekning(kontrollPeriodeForMedl)
        val dekning = Dekning.from(gjeldendeDekning)

        return when {
            dekning == null || dekning.uavklartForYtelse(ytelse) -> ja(regelId)
            else -> nei(regelId)
        }
    }

    companion object {

        fun fraDatagrunnlag(datagrunnlag: Datagrunnlag): ErBrukersDekningUavklartRegel {
            return ErBrukersDekningUavklartRegel(
                ytelse = datagrunnlag.ytelse,
                medlemskap = datagrunnlag.medlemskap,
                startDatoForYtelse = datagrunnlag.startDatoForYtelse
            )
        }
    }
}
