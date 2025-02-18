package no.nav.medlemskap.regler.v1.registrerteOpplysninger

import no.nav.medlemskap.common.medlCounter
import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.InputPeriode
import no.nav.medlemskap.domene.Kontrollperiode.Companion.kontrollPeriodeForMedl
import no.nav.medlemskap.domene.Kontrollperiode.Companion.startDatoForYtelse
import no.nav.medlemskap.domene.Medlemskap
import no.nav.medlemskap.domene.Medlemskap.Companion.finnesPersonIMedlForKontrollPeriode
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.regler.common.BasisRegel
import no.nav.medlemskap.regler.common.RegelId
import no.nav.medlemskap.regler.common.Resultat
import no.nav.medlemskap.regler.common.Resultat.Companion.ja
import no.nav.medlemskap.regler.common.Resultat.Companion.nei
import java.time.LocalDate

class FinnesOpplysningerIMedlRegel(
    ytelse: Ytelse,
    val periode: InputPeriode,
    val førsteDagForYtelse: LocalDate?,
    val medlemskap: List<Medlemskap> = emptyList()
) : BasisRegel(RegelId.REGEL_A, ytelse) {

    override fun operasjon(): Resultat {
        val kontrollPeriodeForMedl = kontrollPeriodeForMedl(startDatoForYtelse(periode, førsteDagForYtelse))
        if (medlemskap.isNotEmpty()) medlCounter().increment()
        return when {
            medlemskap finnesPersonIMedlForKontrollPeriode kontrollPeriodeForMedl -> ja(regelId)
            else -> nei(regelId)
        }
    }

    companion object {
        fun fraDatagrunnlag(datagrunnlag: Datagrunnlag): FinnesOpplysningerIMedlRegel {
            return FinnesOpplysningerIMedlRegel(
                ytelse = datagrunnlag.ytelse,
                periode = datagrunnlag.periode,
                førsteDagForYtelse = datagrunnlag.førsteDagForYtelse,
                medlemskap = datagrunnlag.medlemskap
            )
        }
    }
}
