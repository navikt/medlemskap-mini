package no.nav.medlemskap.regler.v1.lovvalg

import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.InputPeriode
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.domene.personhistorikk.Personhistorikk
import no.nav.medlemskap.regler.common.RegelId
import no.nav.medlemskap.regler.common.Resultat
import no.nav.medlemskap.regler.common.Resultat.Companion.ja
import no.nav.medlemskap.regler.common.Resultat.Companion.nei
import no.nav.medlemskap.regler.common.Resultat.Companion.uavklart
import java.time.LocalDate

class ErBrukerDoedRegel(
    val pdlPersonHistorikk: Personhistorikk,
    ytelse: Ytelse,
    val inputPeriode: InputPeriode,
    startDatoForYtelse: LocalDate
) : LovvalgRegel(RegelId.REGEL_13, ytelse, startDatoForYtelse) {

    override fun operasjon(): Resultat {
        val erBrukerDoed = pdlPersonHistorikk.erBrukerDoed()
        val erBrukerDoedEtterInputperiode = pdlPersonHistorikk.erBrukerDoedEtterPeriode(inputPeriode)

        if (erBrukerDoed && erBrukerDoedEtterInputperiode) {
            return ja(regelId)
        } else if (erBrukerDoed && !erBrukerDoedEtterInputperiode) {
            return uavklart(regelId)
        }
        return nei(regelId)
    }

    companion object {
        fun fraDatagrunnlag(datagrunnlag: Datagrunnlag): ErBrukerDoedRegel {
            return ErBrukerDoedRegel(
                pdlPersonHistorikk = datagrunnlag.pdlpersonhistorikk,
                ytelse = datagrunnlag.ytelse,
                inputPeriode = datagrunnlag.periode,
                startDatoForYtelse = datagrunnlag.startDatoForYtelse
            )
        }
    }
}
