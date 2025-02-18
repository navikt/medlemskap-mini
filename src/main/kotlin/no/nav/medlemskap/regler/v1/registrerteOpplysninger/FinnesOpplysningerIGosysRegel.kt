package no.nav.medlemskap.regler.v1.registrerteOpplysninger

import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.Oppgave
import no.nav.medlemskap.domene.Oppgave.Companion.finnesAapneOppgaver
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.regler.common.BasisRegel
import no.nav.medlemskap.regler.common.RegelId
import no.nav.medlemskap.regler.common.Resultat
import no.nav.medlemskap.regler.common.Resultat.Companion.ja
import no.nav.medlemskap.regler.common.Resultat.Companion.nei

class FinnesOpplysningerIGosysRegel(
    ytelse: Ytelse,
    val oppgaver: List<Oppgave> = emptyList()
) : BasisRegel(RegelId.REGEL_B, ytelse) {
    override fun operasjon(): Resultat {
        return when {
            oppgaver.finnesAapneOppgaver() -> ja(regelId)
            else -> nei(regelId)
        }
    }

    companion object {

        fun fraDatagrunnlag(datagrunnlag: Datagrunnlag): FinnesOpplysningerIGosysRegel {
            return FinnesOpplysningerIGosysRegel(
                ytelse = datagrunnlag.ytelse,
                oppgaver = datagrunnlag.oppgaver
            )
        }
    }
}
