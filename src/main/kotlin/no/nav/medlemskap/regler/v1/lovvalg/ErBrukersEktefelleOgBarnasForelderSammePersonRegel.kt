package no.nav.medlemskap.regler.v1.lovvalg

import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.domene.barn.DataOmBarn
import no.nav.medlemskap.domene.ektefelle.DataOmEktefelle
import no.nav.medlemskap.regler.common.Funksjoner.harAlle
import no.nav.medlemskap.regler.common.RegelId
import no.nav.medlemskap.regler.common.Resultat
import no.nav.medlemskap.regler.common.Resultat.Companion.ja
import no.nav.medlemskap.regler.common.Resultat.Companion.nei
import java.time.LocalDate

class ErBrukersEktefelleOgBarnasForelderSammePersonRegel(
    ytelse: Ytelse,
    startDatoForYtelse: LocalDate,
    private val dataOmEktefelle: DataOmEktefelle?,
    private val dataOmBarn: List<DataOmBarn>?,
    regelId: RegelId = RegelId.REGEL_11_5_1
) : LovvalgRegel(regelId, ytelse, startDatoForYtelse) {

    override fun operasjon(): Resultat {
        val ektefellesBarn = dataOmEktefelle?.personhistorikkEktefelle?.barn
        val brukersBarn = dataOmBarn

        if (ektefellesBarn != null && brukersBarn != null) {
            return when {
                ektefellesBarn.map { it }.harAlle(brukersBarn.map { it.personhistorikkBarn.ident }) -> ja(regelId)
                else -> nei(regelId)
            }
        }
        return nei(regelId)
    }

    companion object {

        fun fraDatagrunnlag(datagrunnlag: Datagrunnlag): ErBrukersEktefelleOgBarnasForelderSammePersonRegel {
            return ErBrukersEktefelleOgBarnasForelderSammePersonRegel(
                ytelse = datagrunnlag.ytelse,
                startDatoForYtelse = datagrunnlag.startDatoForYtelse,
                dataOmEktefelle = datagrunnlag.dataOmEktefelle,
                dataOmBarn = datagrunnlag.dataOmBarn
            )
        }
    }
}
