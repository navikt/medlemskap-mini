package no.nav.medlemskap.regler.v1.udi

import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.Kontrollperiode
import no.nav.medlemskap.domene.Oppholdstillatelse
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.regler.common.BasisRegel
import no.nav.medlemskap.regler.common.RegelId
import no.nav.medlemskap.regler.common.Resultat
import no.nav.medlemskap.regler.common.Resultat.Companion.ja
import no.nav.medlemskap.regler.common.Resultat.Companion.nei
import no.nav.medlemskap.regler.common.Resultat.Companion.uavklart
import java.time.LocalDate

class GyldigOppholdstillatelseIKontrollperiodeRegel(
    ytelse: Ytelse,
    private val oppholdstillatelse: Oppholdstillatelse?,
    private val startDatoForYtelse: LocalDate,
    regelId: RegelId = RegelId.REGEL_19_3
) : BasisRegel(regelId, ytelse) {

    override fun operasjon(): Resultat {

        if (oppholdstillatelse?.gjeldendeOppholdsstatus == null || oppholdstillatelse.arbeidsadgang == null) {
            return uavklart(regelId)
        }

        val kontrollperiode = Kontrollperiode.kontrollPeriodeForOppholdstillatelse(startDatoForYtelse)
        if (oppholdstillatelse.harGyldigOppholdstillatelseForPeriode(kontrollperiode.periode)) {
            return ja(regelId)
        }

        return nei(regelId)
    }

    companion object {

        fun fraDatagrunnlag(datagrunnlag: Datagrunnlag): GyldigOppholdstillatelseIKontrollperiodeRegel {
            return GyldigOppholdstillatelseIKontrollperiodeRegel(
                ytelse = datagrunnlag.ytelse,
                oppholdstillatelse = datagrunnlag.oppholdstillatelse,
                startDatoForYtelse = datagrunnlag.startDatoForYtelse
            )
        }
    }
}
