package no.nav.medlemskap.regler.v1.medlemskap

import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.Medlemskap
import no.nav.medlemskap.domene.Medlemskap.Companion.finnesUavklartePerioder
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.regler.common.RegelId
import no.nav.medlemskap.regler.common.Resultat
import no.nav.medlemskap.regler.common.Resultat.Companion.ja
import no.nav.medlemskap.regler.common.Resultat.Companion.nei
import java.time.LocalDate

class ErPerioderAvklartRegel(
    ytelse: Ytelse,
    val medlemskap: List<Medlemskap>,
    startDatoForYtelse: LocalDate
) : MedlemskapRegel(RegelId.REGEL_1_1, ytelse, startDatoForYtelse, medlemskap) {

    override fun operasjon(): Resultat {
        return when {
            medlemskap finnesUavklartePerioder kontrollPeriodeForMedl -> nei(regelId)
            else -> ja(regelId)
        }
    }

    companion object {

        fun fraDatagrunnlag(datagrunnlag: Datagrunnlag): ErPerioderAvklartRegel {
            return ErPerioderAvklartRegel(
                ytelse = datagrunnlag.ytelse,
                medlemskap = datagrunnlag.medlemskap,
                startDatoForYtelse = datagrunnlag.startDatoForYtelse
            )
        }
    }
}
