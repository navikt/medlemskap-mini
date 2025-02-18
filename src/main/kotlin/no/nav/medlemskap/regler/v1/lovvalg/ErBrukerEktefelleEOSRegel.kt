package no.nav.medlemskap.regler.v1.lovvalg

import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.domene.ektefelle.DataOmEktefelle
import no.nav.medlemskap.domene.personhistorikk.Statsborgerskap.Companion.erEøsBorger
import no.nav.medlemskap.regler.common.RegelId
import no.nav.medlemskap.regler.common.Resultat
import no.nav.medlemskap.regler.common.Resultat.Companion.ja
import no.nav.medlemskap.regler.common.Resultat.Companion.nei
import java.time.LocalDate

class ErBrukerEktefelleEOSRegel(
    ytelse: Ytelse,
    startDatoForYtelse: LocalDate,
    private val dataOmEktefelle: DataOmEktefelle?,
    regelId: RegelId = RegelId.REGEL_29
) : LovvalgRegel(regelId, ytelse, startDatoForYtelse) {

    override fun operasjon(): Resultat {
        val ektefelle = dataOmEktefelle?.personhistorikkEktefelle

        if (ektefelle != null) {
            return when {
                ektefelle.statsborgerskap.erEøsBorger(kontrollPeriodeForPersonhistorikk) -> ja(regelId)
                else -> nei(regelId)
            }
        }
        return nei(regelId)
    }

    companion object {

        fun fraDatagrunnlag(datagrunnlag: Datagrunnlag): ErBrukerEktefelleEOSRegel {
            return ErBrukerEktefelleEOSRegel(
                ytelse = datagrunnlag.ytelse,
                startDatoForYtelse = datagrunnlag.startDatoForYtelse,
                dataOmEktefelle = datagrunnlag.dataOmEktefelle
            )
        }
    }
}
