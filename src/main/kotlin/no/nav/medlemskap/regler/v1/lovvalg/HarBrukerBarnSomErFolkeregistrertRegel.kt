package no.nav.medlemskap.regler.v1.lovvalg

import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.domene.barn.DataOmBarn
import no.nav.medlemskap.domene.personhistorikk.Adresse.Companion.adresserForKontrollperiode
import no.nav.medlemskap.domene.personhistorikk.Adresse.Companion.landkodeForIkkeHistoriskeAdresserForKontrollperiode
import no.nav.medlemskap.regler.common.Funksjoner.isNotNullOrEmpty
import no.nav.medlemskap.regler.common.RegelId
import no.nav.medlemskap.regler.common.Resultat
import no.nav.medlemskap.regler.common.Resultat.Companion.ja
import no.nav.medlemskap.regler.common.Resultat.Companion.nei
import no.nav.medlemskap.regler.common.Resultat.Companion.uavklart
import java.time.LocalDate

class HarBrukerBarnSomErFolkeregistrertRegel(
    ytelse: Ytelse,
    startDatoForYtelse: LocalDate,
    private val dataOmbarn: List<DataOmBarn>?,
    regelId: RegelId = RegelId.REGEL_11_2_2
) : LovvalgRegel(regelId, ytelse, startDatoForYtelse) {

    override fun operasjon(): Resultat {
        val barn = dataOmbarn?.map { it.personhistorikkBarn }?.filter {
            it.bostedsadresser.adresserForKontrollperiode(kontrollPeriodeForPersonhistorikk).isNotNullOrEmpty()
        }

        val harBarnBosattINorge = barn?.any {
            erPersonBosattINorge(
                it.bostedsadresser.adresserForKontrollperiode(kontrollPeriodeForPersonhistorikk),
                it.kontaktadresser.landkodeForIkkeHistoriskeAdresserForKontrollperiode(
                    kontrollPeriodeForPersonhistorikk
                ),
                it.oppholdsadresser.landkodeForIkkeHistoriskeAdresserForKontrollperiode(
                    kontrollPeriodeForPersonhistorikk
                )
            )
        }

        val harBarnSomIkkeErBosattINorge = barn?.any {
            !erPersonBosattINorge(
                it.bostedsadresser.adresserForKontrollperiode(kontrollPeriodeForPersonhistorikk),
                it.kontaktadresser.landkodeForIkkeHistoriskeAdresserForKontrollperiode(
                    kontrollPeriodeForPersonhistorikk
                ),
                it.oppholdsadresser.landkodeForIkkeHistoriskeAdresserForKontrollperiode(
                    kontrollPeriodeForPersonhistorikk
                )
            )
        }

        if (harBarnBosattINorge!! && harBarnSomIkkeErBosattINorge!!) {
            return uavklart(regelId)
        }

        return if (harBarnBosattINorge) {
            ja(regelId)
        } else {
            nei(regelId)
        }
    }

    companion object {

        fun fraDatagrunnlag(datagrunnlag: Datagrunnlag, regelId: RegelId): HarBrukerBarnSomErFolkeregistrertRegel {
            return HarBrukerBarnSomErFolkeregistrertRegel(
                ytelse = datagrunnlag.ytelse,
                startDatoForYtelse = datagrunnlag.startDatoForYtelse,
                dataOmbarn = datagrunnlag.dataOmBarn,
                regelId = regelId
            )
        }
    }
}
