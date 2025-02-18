package no.nav.medlemskap.regler.v1.udi

import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.Oppholdstillatelse
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.domene.personhistorikk.Statsborgerskap
import no.nav.medlemskap.domene.personhistorikk.Statsborgerskap.Companion.erBritiskBorger
import no.nav.medlemskap.regler.common.RegelId
import no.nav.medlemskap.regler.common.Resultat
import no.nav.medlemskap.regler.common.Resultat.Companion.ja
import no.nav.medlemskap.regler.common.Resultat.Companion.nei
import no.nav.medlemskap.regler.v1.lovvalg.LovvalgRegel
import java.time.LocalDate

class HarBritiskBrukerEOSellerEFTAOpphold(
    ytelse: Ytelse,
    private val statsborgerskap: List<Statsborgerskap>,
    private val oppholdstillatelse: Oppholdstillatelse?,
    startDatoForYtelse: LocalDate,
    regelId: RegelId = RegelId.REGEL_30
) : LovvalgRegel(regelId, ytelse, startDatoForYtelse) {

    override fun operasjon(): Resultat {
        if (oppholdstillatelse?.gjeldendeOppholdsstatus?.eosellerEFTAOpphold != null &&
            statsborgerskap.erBritiskBorger(kontrollPeriodeForPersonhistorikk)
        ) {
            return ja(regelId)
        }

        return nei(regelId)
    }

    companion object {

        fun fraDatagrunnlag(datagrunnlag: Datagrunnlag): HarBritiskBrukerEOSellerEFTAOpphold {
            return HarBritiskBrukerEOSellerEFTAOpphold(
                ytelse = datagrunnlag.ytelse,
                oppholdstillatelse = datagrunnlag.oppholdstillatelse,
                statsborgerskap = datagrunnlag.pdlpersonhistorikk.statsborgerskap,
                startDatoForYtelse = datagrunnlag.startDatoForYtelse
            )
        }
    }
}
