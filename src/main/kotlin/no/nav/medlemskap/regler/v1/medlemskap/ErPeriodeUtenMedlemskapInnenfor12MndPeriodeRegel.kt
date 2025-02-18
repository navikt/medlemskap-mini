package no.nav.medlemskap.regler.v1.medlemskap

import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.Medlemskap
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.regler.common.RegelId
import no.nav.medlemskap.regler.common.Resultat
import java.time.LocalDate

class ErPeriodeUtenMedlemskapInnenfor12MndPeriodeRegel(
    ytelse: Ytelse,
    startDatoForYtelse: LocalDate,
    medlemskap: List<Medlemskap>
) : MedlemskapRegel(RegelId.REGEL_1_3_1, ytelse, startDatoForYtelse, medlemskap) {

    override fun operasjon(): Resultat {
        return erMedlemskapPeriodeOver12MndPeriode(false)
    }

    companion object {
        fun fraDatagrunnlag(datagrunnlag: Datagrunnlag): ErPeriodeUtenMedlemskapInnenfor12MndPeriodeRegel {
            return ErPeriodeUtenMedlemskapInnenfor12MndPeriodeRegel(
                ytelse = datagrunnlag.ytelse,
                medlemskap = datagrunnlag.medlemskap,
                startDatoForYtelse = datagrunnlag.startDatoForYtelse
            )
        }
    }
}
