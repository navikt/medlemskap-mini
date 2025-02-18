package no.nav.medlemskap.regler.v1.medlemskap

import no.nav.medlemskap.common.regelUendretCounterMidlertidig
import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.Medlemskap
import no.nav.medlemskap.domene.Medlemskap.Companion.tidligsteFraOgMedDatoForMedl
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.domene.arbeidsforhold.Arbeidsforhold
import no.nav.medlemskap.domene.arbeidsforhold.Arbeidsforhold.Companion.arbeidsforholdForDato
import no.nav.medlemskap.regler.common.RegelId
import no.nav.medlemskap.regler.common.Resultat
import no.nav.medlemskap.regler.common.Resultat.Companion.ja
import no.nav.medlemskap.regler.common.Resultat.Companion.nei
import no.nav.medlemskap.regler.common.Svar
import java.time.LocalDate

class ErArbeidsforholdUendretRegel(
    ytelse: Ytelse,
    startDatoForYtelse: LocalDate,
    private val medlemskap: List<Medlemskap>,
    private val arbeidsforhold: List<Arbeidsforhold>,
    regelId: RegelId
) : MedlemskapRegel(regelId, ytelse, startDatoForYtelse, medlemskap) {
    override fun operasjon(): Resultat {
        return erBrukersArbeidsforholdUendret(regelId)
    }

    private fun erBrukersArbeidsforholdUendret(regelId: RegelId): Resultat {
        if (harSammeArbeidsforholdSidenFomDatoFraMedl()) {
            regelUendretCounterMidlertidig(regelId, Svar.JA, ytelse).increment()
            return ja(regelId)
        } else {
            regelUendretCounterMidlertidig(regelId, Svar.NEI, ytelse).increment()
            return nei(regelId)
        }
        /* Tas inn igjen når man ikke lenger trenger egen Grafana-counter for denne (når dekning kan gis i response)
        when {
            harSammeArbeidsforholdSidenFomDatoFraMedl() -> ja()
            else -> nei()
        }
         */
    }

    private fun harSammeArbeidsforholdSidenFomDatoFraMedl(): Boolean =
        arbeidsforhold.arbeidsforholdForDato(medlemskap.tidligsteFraOgMedDatoForMedl(kontrollPeriodeForMedl)).isNotEmpty() &&
            (
                ulikeArbeidsforholdMenSammeArbeidsgiver() ||
                    arbeidsforhold.arbeidsforholdForDato(medlemskap.tidligsteFraOgMedDatoForMedl(kontrollPeriodeForMedl)) ==
                    arbeidsforhold.arbeidsforholdForDato(kontrollPeriodeForMedl.tom.plusDays(1))
                )

    private fun ulikeArbeidsforholdMenSammeArbeidsgiver() =
        (
            arbeidsforhold.arbeidsforholdForDato(medlemskap.tidligsteFraOgMedDatoForMedl(kontrollPeriodeForMedl)) != arbeidsforhold.arbeidsforholdForDato(kontrollPeriodeForMedl.tom) &&
                arbeidsforhold.arbeidsforholdForDato(medlemskap.tidligsteFraOgMedDatoForMedl(kontrollPeriodeForMedl)).map { it.arbeidsgiver.organisasjonsnummer } ==
                arbeidsforhold.arbeidsforholdForDato(kontrollPeriodeForMedl.tom.plusDays(1)).map { it.arbeidsgiver.organisasjonsnummer }
            )

    companion object {

        fun fraDatagrunnlag(datagrunnlag: Datagrunnlag, regelId: RegelId): ErArbeidsforholdUendretRegel {
            return ErArbeidsforholdUendretRegel(
                ytelse = datagrunnlag.ytelse,
                startDatoForYtelse = datagrunnlag.startDatoForYtelse,
                medlemskap = datagrunnlag.medlemskap,
                arbeidsforhold = datagrunnlag.arbeidsforhold,
                regelId = regelId
            )
        }
    }
}
