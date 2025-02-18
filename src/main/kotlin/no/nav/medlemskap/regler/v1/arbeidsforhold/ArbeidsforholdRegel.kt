package no.nav.medlemskap.regler.v1.arbeidsforhold

import no.nav.medlemskap.domene.Kontrollperiode
import no.nav.medlemskap.domene.Kontrollperiode.Companion.kontrollPeriodeForArbeidsforhold
import no.nav.medlemskap.domene.Kontrollperiode.Companion.kontrollperiodeForBarneBriller
import no.nav.medlemskap.domene.Kontrollperiode.Companion.kontrollperiodeForSykepenger
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.regler.common.BasisRegel
import no.nav.medlemskap.regler.common.RegelId
import java.time.LocalDate

abstract class ArbeidsforholdRegel(
    regelId: RegelId,
    ytelse: Ytelse,
    startDatoForYtelse: LocalDate
) : BasisRegel(regelId, ytelse) {
    val kontrollPeriodeForArbeidsforhold = kontrollPeriodeForArbeidsforhold(startDatoForYtelse)
    val kontrollperiodeForSykepenger = kontrollperiodeForSykepenger(startDatoForYtelse)
    val kontrollperiodeForBarnbriller = kontrollperiodeForBarneBriller(startDatoForYtelse)

    fun finnKOntrollPeriode(ytelse: Ytelse): Kontrollperiode {
        when (ytelse){
            Ytelse.SYKEPENGER -> return kontrollperiodeForSykepenger
            Ytelse.BARNE_BRILLER -> return kontrollperiodeForBarnbriller
            Ytelse.DAGPENGER -> return kontrollperiodeForSykepenger
            Ytelse.ENSLIG_FORSORGER -> return kontrollperiodeForSykepenger
            Ytelse.LOVME -> return kontrollperiodeForSykepenger
            Ytelse.LOVME_GCP -> return kontrollperiodeForSykepenger
            Ytelse.MEDLEMSKAP_BARN -> return kontrollperiodeForBarnbriller
            Ytelse.MIN_VEI -> return kontrollperiodeForSykepenger
            Ytelse.LOVME_FUNKSJONELLE_TESTER -> return kontrollperiodeForSykepenger
        }
    }
}
