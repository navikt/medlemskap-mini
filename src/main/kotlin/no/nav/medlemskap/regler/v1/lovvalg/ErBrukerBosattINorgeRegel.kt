package no.nav.medlemskap.regler.v1.lovvalg

import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.Landkode
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.domene.personhistorikk.Adresse
import no.nav.medlemskap.domene.personhistorikk.Adresse.Companion.landkodeForIkkeHistoriskeAdresserForKontrollperiode
import no.nav.medlemskap.domene.personhistorikk.Adresse.Companion.landkodeTilAdresserForKontrollPeriode
import no.nav.medlemskap.regler.common.Funksjoner.erIkkeTom
import no.nav.medlemskap.regler.common.Funksjoner.erTom
import no.nav.medlemskap.regler.common.RegelId.REGEL_10
import no.nav.medlemskap.regler.common.Resultat
import no.nav.medlemskap.regler.common.Resultat.Companion.ja
import no.nav.medlemskap.regler.common.Resultat.Companion.nei
import java.time.LocalDate

class ErBrukerBosattINorgeRegel(
    val kontaktadresser: List<Adresse>,
    val bostedsadresser: List<Adresse>,
    val oppholdsadresser: List<Adresse>,
    ytelse: Ytelse,
    startDatoForYtelse: LocalDate
) : LovvalgRegel(REGEL_10, ytelse, startDatoForYtelse) {

    override fun operasjon(): Resultat {
        val landkoderBostedsadresser = bostedsadresser.landkodeTilAdresserForKontrollPeriode(kontrollPeriodeForPersonhistorikk)
        val kontaktadresserLandkoder = kontaktadresser.landkodeForIkkeHistoriskeAdresserForKontrollperiode(kontrollPeriodeForPersonhistorikk)
        val oppholsadresserLandkoder = oppholdsadresser.landkodeForIkkeHistoriskeAdresserForKontrollperiode(kontrollPeriodeForPersonhistorikk)

        return when {
            landkoderBostedsadresser.erIkkeTom() && landkoderBostedsadresser.all { Landkode.erNorsk(it) } &&
                (kontaktadresserLandkoder.all { Landkode.erNorsk(it) } || kontaktadresserLandkoder.erTom())
                && (oppholsadresserLandkoder.all { Landkode.erNorsk(it) } || oppholsadresserLandkoder.erTom()) -> ja(regelId)
            else -> nei(regelId)
        }
    }

    companion object {
        fun fraDatagrunnlag(datagrunnlag: Datagrunnlag): ErBrukerBosattINorgeRegel {
            return ErBrukerBosattINorgeRegel(
                kontaktadresser = datagrunnlag.pdlpersonhistorikk.kontaktadresser,
                bostedsadresser = datagrunnlag.pdlpersonhistorikk.bostedsadresser,
                oppholdsadresser = datagrunnlag.pdlpersonhistorikk.oppholdsadresser,
                ytelse = datagrunnlag.ytelse,
                startDatoForYtelse = datagrunnlag.startDatoForYtelse
            )
        }
    }
}
