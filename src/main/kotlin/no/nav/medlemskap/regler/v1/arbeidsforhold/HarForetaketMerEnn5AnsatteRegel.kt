package no.nav.medlemskap.regler.v1.arbeidsforhold

import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.domene.arbeidsforhold.Arbeidsforhold
import no.nav.medlemskap.domene.arbeidsforhold.Arbeidsforhold.Companion.antallAnsatteHosArbeidsgivere
import no.nav.medlemskap.domene.arbeidsforhold.Arbeidsforhold.Companion.antallAnsatteHosArbeidsgiversJuridiskeEnheter
import no.nav.medlemskap.domene.arbeidsforhold.Arbeidsforhold.Companion.arbeidsforholdForKontrollPeriodeMedStillingsprosentOver0
import no.nav.medlemskap.domene.personhistorikk.Statsborgerskap
import no.nav.medlemskap.regler.common.Funksjoner.finnesMindreEnn
import no.nav.medlemskap.regler.common.RegelId
import no.nav.medlemskap.regler.common.Resultat
import no.nav.medlemskap.regler.common.Resultat.Companion.ja
import no.nav.medlemskap.regler.common.Resultat.Companion.nei
import java.time.LocalDate

class HarForetaketMerEnn5AnsatteRegel(
    ytelse: Ytelse,
    startDatoForYtelse: LocalDate,
    private val arbeidsforhold: List<Arbeidsforhold>,
    private val statsborgerskap: List<Statsborgerskap>,
    regelId: RegelId = RegelId.REGEL_5
) : ArbeidsforholdRegel(regelId, ytelse, startDatoForYtelse) {

    override fun operasjon(): Resultat {

        val arbeidsforholdMedMinstEnArbeidsavtaleOver0Stillingsprosent = arbeidsforhold.arbeidsforholdForKontrollPeriodeMedStillingsprosentOver0(kontrollPeriodeForArbeidsforhold)

        val finnesMindreEnn6AnsatteIArbeidsgivernesForetak = arbeidsforholdMedMinstEnArbeidsavtaleOver0Stillingsprosent.antallAnsatteHosArbeidsgivere(kontrollPeriodeForArbeidsforhold) finnesMindreEnn 6
        val finnesMindreEnn6AnsatteIArbeidsgivernesJuridiskeEnheter = arbeidsforholdMedMinstEnArbeidsavtaleOver0Stillingsprosent.antallAnsatteHosArbeidsgiversJuridiskeEnheter(kontrollPeriodeForArbeidsforhold) finnesMindreEnn 6

        if (arbeidsforhold.isEmpty() || finnesMindreEnn6AnsatteIArbeidsgivernesForetak && finnesMindreEnn6AnsatteIArbeidsgivernesJuridiskeEnheter) {
            return nei(regelId)
        }

        return ja(regelId)
    }

    companion object {

        fun fraDatagrunnlag(datagrunnlag: Datagrunnlag): HarForetaketMerEnn5AnsatteRegel {
            return HarForetaketMerEnn5AnsatteRegel(
                ytelse = datagrunnlag.ytelse,
                startDatoForYtelse = datagrunnlag.startDatoForYtelse,
                arbeidsforhold = datagrunnlag.arbeidsforhold,
                statsborgerskap = datagrunnlag.pdlpersonhistorikk.statsborgerskap
            )
        }
    }
}
