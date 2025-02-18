package no.nav.medlemskap.regler.v1.medlemskap

import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.Medlemskap
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.regler.common.RegelId
import no.nav.medlemskap.regler.common.Resultat
import java.time.LocalDate

class ErPeriodeMedMedlemskapInnenfor12MndPeriodeRegel(
    ytelse: Ytelse,
    startDatoForYtelse: LocalDate,
    medlemskap: List<Medlemskap>
) : MedlemskapRegel(RegelId.REGEL_1_4, ytelse, startDatoForYtelse, medlemskap) {

    override fun operasjon(): Resultat {
        return erMedlemskapPeriodeOver12MndPeriode(true)
    }

    companion object {
        fun fraDatagrunnlag(datagrunnlag: Datagrunnlag): ErPeriodeMedMedlemskapInnenfor12MndPeriodeRegel {
            return ErPeriodeMedMedlemskapInnenfor12MndPeriodeRegel(
                ytelse = datagrunnlag.ytelse,
                startDatoForYtelse = datagrunnlag.startDatoForYtelse,
                medlemskap = datagrunnlag.medlemskap
            )
        }
    }
}
