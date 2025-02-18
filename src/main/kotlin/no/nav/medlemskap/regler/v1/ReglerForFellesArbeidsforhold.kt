package no.nav.medlemskap.regler.v1

import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.InputPeriode
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.regler.common.RegelId
import no.nav.medlemskap.regler.common.RegelId.*
import no.nav.medlemskap.regler.common.Regelflyt
import no.nav.medlemskap.regler.common.Regelflyt.Companion.konklusjonUavklart
import no.nav.medlemskap.regler.common.Regelflyt.Companion.regelflytJa
import no.nav.medlemskap.regler.common.Regler
import no.nav.medlemskap.regler.common.Svar
import no.nav.medlemskap.regler.v1.arbeidsforhold.ErSummenAvPermisjonenMerEnn30DagerSiste12Mnd

class ReglerForFellesArbeidsforhold(
    val periode: InputPeriode,
    ytelse: Ytelse,
    regelFactory: RegelFactory,
    overstyrteRegler: Map<RegelId, Svar>
) : Regler(ytelse, regelFactory, overstyrteRegler) {

    override fun hentHovedflyt(): Regelflyt {

        /*
        val erBrukerArbeidstakerIkontrollperiodeForStønadsområdeFlyt = lagRegelflyt(
            regel = hentRegel(REGEL_21),
            hvisJa = regelflytJa(ytelse, REGEL_FELLES_ARBEIDSFORHOLD),
            hvisNei = konklusjonUavklart(ytelse, REGEL_FELLES_ARBEIDSFORHOLD)
        )

         */

        val HarBrukerSammenhengendeArbeidsforholdRegelFlyt = lagRegelflyt(
            regel = hentRegel(REGEL_3),
            hvisJa = regelflytJa(ytelse, REGEL_FELLES_ARBEIDSFORHOLD),
            hvisNei = konklusjonUavklart(ytelse, REGEL_FELLES_ARBEIDSFORHOLD),
        )

        val ErPeriodenForUtenlandsoppholdetInnenforSiste12MånederFlyt = lagRegelflyt(
            regel = hentRegel(REGEL_25),
            hvisJa = konklusjonUavklart(ytelse, REGEL_FELLES_ARBEIDSFORHOLD),
            hvisNei = HarBrukerSammenhengendeArbeidsforholdRegelFlyt,
        )

        val HarBrukerUtenlandsoppholdIArbeidsforholdetFlyt = lagRegelflyt(
            regel = hentRegel(REGEL_22),
            hvisJa = ErPeriodenForUtenlandsoppholdetInnenforSiste12MånederFlyt,
            hvisNei = HarBrukerSammenhengendeArbeidsforholdRegelFlyt
        )


        val ErSummenAvPermisjonenMerEnn30DagerSiste12Mnd = lagRegelflyt(
            regel = hentRegel(REGEL_33),
            hvisJa = konklusjonUavklart(ytelse, REGEL_FELLES_ARBEIDSFORHOLD),
            hvisNei = HarBrukerUtenlandsoppholdIArbeidsforholdetFlyt
        )

        val HarBrukerPermisjonSiste12MånederFlyt = lagRegelflyt(
            regel = hentRegel(REGEL_32),
            hvisJa = ErSummenAvPermisjonenMerEnn30DagerSiste12Mnd,
            hvisNei = HarBrukerUtenlandsoppholdIArbeidsforholdetFlyt
        )
        val HarPermitteringSiste12MånederFlyt = lagRegelflyt(
            regel = hentRegel(REGEL_15),
            hvisJa = konklusjonUavklart(ytelse, REGEL_FELLES_ARBEIDSFORHOLD),
            hvisNei = HarBrukerPermisjonSiste12MånederFlyt
        )

        val ErBrukerFrilanserFlyt = lagRegelflyt(
            regel = hentRegel(REGEL_17_1),
            hvisJa = konklusjonUavklart(ytelse, REGEL_FELLES_ARBEIDSFORHOLD),
            hvisNei = HarPermitteringSiste12MånederFlyt
        )


        val HarBrukerArbeidsforholdFlyt = lagRegelflyt(
            regel = hentRegel(REGEL_17),
            hvisJa = ErBrukerFrilanserFlyt,
            hvisNei = konklusjonUavklart(ytelse, REGEL_FELLES_ARBEIDSFORHOLD)
        )

        return HarBrukerArbeidsforholdFlyt
    }

    companion object {
        fun fraDatagrunnlag(datagrunnlag: Datagrunnlag, overstyrteRegler: Map<RegelId, Svar> = emptyMap()): ReglerForFellesArbeidsforhold {

            return ReglerForFellesArbeidsforhold(
                periode = datagrunnlag.periode,
                ytelse = datagrunnlag.ytelse,
                regelFactory = RegelFactory(datagrunnlag),
                overstyrteRegler = overstyrteRegler
            )
        }
    }
}
