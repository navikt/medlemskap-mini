package no.nav.medlemskap.regler.v1.medlemskap

import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.Medlemskap
import no.nav.medlemskap.domene.Medlemskap.Companion.harUSAellerCANunntakiKontrollperiode
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.regler.common.RegelId
import no.nav.medlemskap.regler.common.Resultat
import no.nav.medlemskap.regler.common.Resultat.Companion.ja
import no.nav.medlemskap.regler.common.Resultat.Companion.nei
import java.time.LocalDate

class ErUnntaketEtterUSAellerCANADAavtaleRegel(
    ytelse: Ytelse,
    startDatoForYtelse: LocalDate,
    val medlemskap: List<Medlemskap>
) : MedlemskapRegel(RegelId.REGEL_1_3_3, ytelse, startDatoForYtelse, medlemskap) {

    override fun operasjon(): Resultat {
        return when {
            medlemskap.harUSAellerCANunntakiKontrollperiode(kontrollPeriodeForMedl) -> ja(regelId)
            else -> nei(regelId)
        }
    }

    companion object {
        fun fraDatagrunnlag(datagrunnlag: Datagrunnlag): ErUnntaketEtterUSAellerCANADAavtaleRegel {
            return ErUnntaketEtterUSAellerCANADAavtaleRegel(
                ytelse = datagrunnlag.ytelse,
                medlemskap = datagrunnlag.medlemskap,
                startDatoForYtelse = datagrunnlag.startDatoForYtelse
            )
        }
    }
}
