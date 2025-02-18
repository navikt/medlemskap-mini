package no.nav.medlemskap.regler.v1.lovvalg

import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.domene.ektefelle.DataOmEktefelle
import no.nav.medlemskap.domene.personhistorikk.Adresse.Companion.adresserForKontrollperiode
import no.nav.medlemskap.domene.personhistorikk.Adresse.Companion.landkodeTilAdresserForKontrollPeriode
import no.nav.medlemskap.regler.common.RegelId
import no.nav.medlemskap.regler.common.Resultat
import no.nav.medlemskap.regler.common.Resultat.Companion.ja
import no.nav.medlemskap.regler.common.Resultat.Companion.nei
import java.time.LocalDate

class ErBrukersEktefelleBosattINorgeRegel(
    ytelse: Ytelse,
    startDatoForYtelse: LocalDate,
    private val dataOmEktefelle: DataOmEktefelle?,
    regelId: RegelId = RegelId.REGEL_11_3_1
) : LovvalgRegel(regelId, ytelse, startDatoForYtelse) {

    override fun operasjon(): Resultat {

        if (dataOmEktefelle != null) {
            val ektefelle = dataOmEktefelle.personhistorikkEktefelle

            val bostedsadresserTilEktefelle =
                ektefelle.bostedsadresser.adresserForKontrollperiode(kontrollPeriodeForPersonhistorikk)
            val kontaktadresseTilEktefelle =
                ektefelle.kontaktadresser.landkodeTilAdresserForKontrollPeriode(kontrollPeriodeForPersonhistorikk)
            val oppholdsadresseTilEktefelle =
                ektefelle.oppholdsadresser.landkodeTilAdresserForKontrollPeriode(kontrollPeriodeForPersonhistorikk)

            return when {
                erPersonBosattINorge(
                    bostedsadresserTilEktefelle,
                    kontaktadresseTilEktefelle,
                    oppholdsadresseTilEktefelle
                ) -> ja(RegelId.REGEL_11_3_1)
                else -> nei(regelId)
            }
        }
        // Denne vil ikke skje
        return nei(regelId)
    }

    companion object {

        fun fraDatagrunnlag(datagrunnlag: Datagrunnlag, regelId: RegelId): ErBrukersEktefelleBosattINorgeRegel {
            return ErBrukersEktefelleBosattINorgeRegel(
                ytelse = datagrunnlag.ytelse,
                startDatoForYtelse = datagrunnlag.startDatoForYtelse,
                dataOmEktefelle = datagrunnlag.dataOmEktefelle,
                regelId = regelId
            )
        }
    }
}
