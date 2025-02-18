package no.nav.medlemskap.regler.v1.registrerteOpplysninger

import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.InputPeriode
import no.nav.medlemskap.domene.Journalpost
import no.nav.medlemskap.domene.Journalpost.Companion.finnesDokumenterMedTillatteTeamer
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.regler.common.BasisRegel
import no.nav.medlemskap.regler.common.RegelId
import no.nav.medlemskap.regler.common.Resultat
import no.nav.medlemskap.regler.common.Resultat.Companion.ja
import no.nav.medlemskap.regler.common.Resultat.Companion.nei
import java.time.LocalDate

class FinnesOpplysningerIJoarkRegel(
    ytelse: Ytelse,
    val periode: InputPeriode,
    val førsteDagForYtelse: LocalDate?,
    private val dokument: List<Journalpost> = emptyList()
) : BasisRegel(RegelId.REGEL_C, ytelse) {

    override fun operasjon(): Resultat {
        return when {
            dokument.finnesDokumenterMedTillatteTeamer() -> ja(regelId)
            else -> nei(regelId)
        }
    }

    companion object {

        fun fraDatagrunnlag(datagrunnlag: Datagrunnlag): FinnesOpplysningerIJoarkRegel {
            return FinnesOpplysningerIJoarkRegel(
                ytelse = datagrunnlag.ytelse,
                dokument = datagrunnlag.dokument,
                periode = datagrunnlag.periode,
                førsteDagForYtelse = datagrunnlag.førsteDagForYtelse

            )
        }
    }
}
