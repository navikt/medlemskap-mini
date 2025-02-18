package no.nav.medlemskap.regler.v1.lovvalg

import no.nav.medlemskap.domene.Brukerinput
import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.regler.common.RegelId
import no.nav.medlemskap.regler.common.Resultat
import no.nav.medlemskap.regler.common.Resultat.Companion.ja
import no.nav.medlemskap.regler.common.Resultat.Companion.nei
import java.time.LocalDate

class HarBrukerJobbetUtenforNorgeRegel(
    ytelse: Ytelse,
    startDatoForYtelse: LocalDate,
    private val arbeidUtenforNorge: Boolean,
    private val brukerInput: Brukerinput,
    regelId: RegelId = RegelId.REGEL_9
) : LovvalgRegel(regelId, ytelse, startDatoForYtelse) {

    override fun operasjon(): Resultat {
        if (brukerInput.oppholdstilatelse?.svar != null ||
            brukerInput.utfortAarbeidUtenforNorge?.svar != null ||
            brukerInput.oppholdUtenforNorge?.svar != null ||
            brukerInput.oppholdUtenforEos?.svar != null
        ) {
            return nei(regelId)
        }
        return when {
            arbeidUtenforNorge -> ja(regelId)
            else -> nei(regelId)
        }
    }

    companion object {

        fun fraDatagrunnlag(datagrunnlag: Datagrunnlag): HarBrukerJobbetUtenforNorgeRegel {
            return HarBrukerJobbetUtenforNorgeRegel(
                ytelse = datagrunnlag.ytelse,
                startDatoForYtelse = datagrunnlag.startDatoForYtelse,
                arbeidUtenforNorge = datagrunnlag.brukerinput.arbeidUtenforNorge,
                brukerInput = datagrunnlag.brukerinput
            )
        }
    }
}
