package no.nav.medlemskap.regler.v1.udi

import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.Oppholdstillatelse
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.domene.ektefelle.DataOmEktefelle
import no.nav.medlemskap.domene.personhistorikk.Statsborgerskap.Companion.erBritiskBorger
import no.nav.medlemskap.regler.common.RegelId
import no.nav.medlemskap.regler.common.Resultat
import no.nav.medlemskap.regler.common.Resultat.Companion.ja
import no.nav.medlemskap.regler.common.Resultat.Companion.nei
import no.nav.medlemskap.regler.v1.lovvalg.LovvalgRegel
import java.time.LocalDate

class HarBrukerEOSellerEFTAOppholdOgBritiskEktefelleRegel(
    ytelse: Ytelse,
    private val oppholdstillatelse: Oppholdstillatelse?,
    private val dataOmEktefelle: DataOmEktefelle?,
    startDatoForYtelse: LocalDate,
    regelId: RegelId = RegelId.REGEL_19_4
) : LovvalgRegel(regelId, ytelse, startDatoForYtelse) {

    override fun operasjon(): Resultat {
        if (oppholdstillatelse?.gjeldendeOppholdsstatus?.eosellerEFTAOpphold != null &&
            dataOmEktefelle?.personhistorikkEktefelle?.statsborgerskap?.erBritiskBorger(kontrollPeriodeForPersonhistorikk) == true
        ) {
            return ja(regelId)
        }

        return nei(regelId)
    }

    companion object {

        fun fraDatagrunnlag(datagrunnlag: Datagrunnlag): HarBrukerEOSellerEFTAOppholdOgBritiskEktefelleRegel {
            return HarBrukerEOSellerEFTAOppholdOgBritiskEktefelleRegel(
                ytelse = datagrunnlag.ytelse,
                dataOmEktefelle = datagrunnlag.dataOmEktefelle,
                oppholdstillatelse = datagrunnlag.oppholdstillatelse,
                startDatoForYtelse = datagrunnlag.startDatoForYtelse
            )
        }
    }
}
