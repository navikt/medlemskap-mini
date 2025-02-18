package no.nav.medlemskap.regler.v1.medlemskap

import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.Medlemskap
import no.nav.medlemskap.domene.Medlemskap.Companion.erPeriodeSammenfallendeMedUnntak
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.domene.arbeidsforhold.Arbeidsforhold
import no.nav.medlemskap.domene.arbeidsforhold.Arbeidsforhold.Companion.AlleArbeidsforholdPerioderIKontrollperiode
import no.nav.medlemskap.regler.common.RegelId
import no.nav.medlemskap.regler.common.Resultat
import no.nav.medlemskap.regler.common.Resultat.Companion.ja
import no.nav.medlemskap.regler.common.Resultat.Companion.nei
import java.time.LocalDate

class ErArbeidsforholdetSammenfallendeMedPeriodenUtenMedlemskapRegel(
    ytelse: Ytelse,
    startDatoForYtelse: LocalDate,
    val arbeidsforhold: List<Arbeidsforhold>,
    val medlemskap: List<Medlemskap>
) : MedlemskapRegel(RegelId.REGEL_1_3_5, ytelse, startDatoForYtelse, medlemskap) {

    override fun operasjon(): Resultat {
        val arbeidsforholdsperioder = arbeidsforhold.AlleArbeidsforholdPerioderIKontrollperiode(kontrollPeriodeForMedl)

        return when {
            medlemskap.erPeriodeSammenfallendeMedUnntak(kontrollPeriodeForMedl, arbeidsforholdsperioder, 7) -> ja(regelId)
            else -> nei(regelId)
        }
    }

    companion object {
        fun fraDatagrunnlag(datagrunnlag: Datagrunnlag): ErArbeidsforholdetSammenfallendeMedPeriodenUtenMedlemskapRegel {
            return ErArbeidsforholdetSammenfallendeMedPeriodenUtenMedlemskapRegel(
                ytelse = datagrunnlag.ytelse,
                medlemskap = datagrunnlag.medlemskap,
                arbeidsforhold = datagrunnlag.arbeidsforhold,
                startDatoForYtelse = datagrunnlag.startDatoForYtelse
            )
        }
    }
}
