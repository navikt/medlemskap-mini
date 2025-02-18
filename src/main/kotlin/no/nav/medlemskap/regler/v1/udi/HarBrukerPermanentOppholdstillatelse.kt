package no.nav.medlemskap.regler.v1.udi

import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.Kontrollperiode
import no.nav.medlemskap.domene.Oppholdstillatelse
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.domene.personhistorikk.Statsborgerskap
import no.nav.medlemskap.regler.common.RegelId
import no.nav.medlemskap.regler.common.Resultat
import no.nav.medlemskap.regler.common.Resultat.Companion.ja
import no.nav.medlemskap.regler.common.Resultat.Companion.nei
import no.nav.medlemskap.regler.v1.lovvalg.LovvalgRegel
import java.time.LocalDate

class HarBrukerPermanentOppholdstillatelse(
    ytelse: Ytelse,
    private val statsborgerskap: List<Statsborgerskap>,
    private val oppholdstillatelse: Oppholdstillatelse?,
    startDatoForYtelse: LocalDate,
    regelId: RegelId = RegelId.REGEL_31
) : LovvalgRegel(regelId, ytelse, startDatoForYtelse) {

    override fun operasjon(): Resultat {
        val kontrollperiode = Kontrollperiode.kontrollPeriodeForOppholdstillatelse(startDatoForYtelse)
        if (oppholdstillatelse?.harPermanentOppholdstillatelse(kontrollperiode.periode) == true) {
            return ja(regelId)
        }

        return nei(regelId)
    }

    companion object {

        fun fraDatagrunnlag(datagrunnlag: Datagrunnlag): HarBrukerPermanentOppholdstillatelse {
            return HarBrukerPermanentOppholdstillatelse(
                ytelse = datagrunnlag.ytelse,
                oppholdstillatelse = datagrunnlag.oppholdstillatelse,
                statsborgerskap = datagrunnlag.pdlpersonhistorikk.statsborgerskap,
                startDatoForYtelse = datagrunnlag.startDatoForYtelse
            )
        }
    }
}
