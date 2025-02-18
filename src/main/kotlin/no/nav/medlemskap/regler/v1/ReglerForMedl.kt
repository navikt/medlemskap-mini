package no.nav.medlemskap.regler.v1

import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.regler.common.*
import no.nav.medlemskap.regler.common.RegelId.*
import no.nav.medlemskap.regler.common.Regelflyt.Companion.konklusjonJa
import no.nav.medlemskap.regler.common.Regelflyt.Companion.konklusjonNei
import no.nav.medlemskap.regler.common.Regelflyt.Companion.konklusjonUavklart
import no.nav.medlemskap.regler.common.Regelflyt.Companion.regelflytJa
import no.nav.medlemskap.regler.v1.medlemskap.*
import no.nav.medlemskap.regler.v1.registrerteOpplysninger.FinnesOpplysningerIGosysRegel
import no.nav.medlemskap.regler.v1.registrerteOpplysninger.FinnesOpplysningerIJoarkRegel
import no.nav.medlemskap.regler.v1.registrerteOpplysninger.FinnesOpplysningerIMedlRegel
import no.nav.medlemskap.regler.v1.registrerteOpplysninger.HarBrukerRegistrerteOpplysningerRegel

class ReglerForMedl(
    ytelse: Ytelse,
    regelFactory: RegelFactory,
    overstyrteRegler: Map<RegelId, Svar>
) : Regler(ytelse, regelFactory, overstyrteRegler) {

    override fun hentHovedflyt(): Regelflyt {
        val harBrukerDekningIMedlFlyt = lagRegelflyt(
            regel = hentRegel(REGEL_1_7),
            hvisJa = konklusjonJa(ytelse, REGEL_MEDL),
            hvisNei = konklusjonUavklart(ytelse, REGEL_MEDL)
        )

        val erDekningUavklartFlyt = lagRegelflyt(
            regel = hentRegel(REGEL_1_6),
            hvisJa = konklusjonUavklart(ytelse, REGEL_MEDL),
            hvisNei = harBrukerDekningIMedlFlyt
        )

        val erArbeidsforholdUendretForBrukerMedMedlemskapFlyt = lagRegelflyt(
            regel = hentRegel(REGEL_1_5),
            hvisJa = erDekningUavklartFlyt,
            hvisNei = konklusjonUavklart(ytelse, REGEL_MEDL)
        )

        val erPeriodeMedMedlemskapInnenfor12MndPeriodeFlyt = lagRegelflyt(
            regel = hentRegel(REGEL_1_4),
            hvisJa = erArbeidsforholdUendretForBrukerMedMedlemskapFlyt,
            hvisNei = konklusjonUavklart(ytelse, REGEL_MEDL)
        )

        val erArbeidsforholdetSammenfallendeMedPeriodenUtenMedlemskapRegelFlyt = lagRegelflyt(
            regel = hentRegel(REGEL_1_3_5),
            hvisJa = konklusjonNei(ytelse, REGEL_MEDL),
            hvisNei = konklusjonUavklart(ytelse, REGEL_MEDL)
        )

        val erDetArbeidsforholdiAaregInnenforUnntaksperiodeFlyt = lagRegelflyt(
            regel = hentRegel(REGEL_1_3_4),
            hvisJa = erArbeidsforholdetSammenfallendeMedPeriodenUtenMedlemskapRegelFlyt,
            hvisNei = konklusjonNei(ytelse, REGEL_MEDL)
        )

        val erPeriodeUtenMedlemskapInnenfor12MndPeriodeFlyt = lagRegelflyt(
            regel = hentRegel(REGEL_1_3_1),
            hvisJa = erDetArbeidsforholdiAaregInnenforUnntaksperiodeFlyt,
            hvisNei = konklusjonUavklart(ytelse, REGEL_MEDL)
        )

        val unntakEtterUSAellerCANADAavtaleFlyt = lagRegelflyt(
            regel = hentRegel(REGEL_1_3_3),
            hvisJa = konklusjonUavklart(ytelse, REGEL_MEDL),
            hvisNei = erPeriodeUtenMedlemskapInnenfor12MndPeriodeFlyt
        )

        val periodeMedMedlemskapFlyt = lagRegelflyt(
            regel = hentRegel(REGEL_1_3),
            hvisJa = erPeriodeMedMedlemskapInnenfor12MndPeriodeFlyt,
            hvisNei = unntakEtterUSAellerCANADAavtaleFlyt
        )

        val periodeMedOgUtenMedlemskapFlyt = lagRegelflyt(
            regel = hentRegel(REGEL_1_2),
            hvisJa = konklusjonUavklart(ytelse, REGEL_MEDL),
            hvisNei = periodeMedMedlemskapFlyt
        )

        val erPerioderAvklartFlyt = lagRegelflyt(
            regel = hentRegel(REGEL_1_1),
            hvisJa = periodeMedOgUtenMedlemskapFlyt,
            hvisNei = konklusjonUavklart(ytelse, REGEL_MEDL)
        )

        val harBrukerRegistrerteOpplysningerFlyt = lagRegelflyt(
            regel = hentRegel(REGEL_OPPLYSNINGER),
            hvisJa = erPerioderAvklartFlyt,
            hvisNei = regelflytJa(ytelse, REGEL_MEDL),
            hvisUavklart = konklusjonUavklart(ytelse, REGEL_MEDL)
        )

        return harBrukerRegistrerteOpplysningerFlyt
    }

    companion object {
        fun fraDatagrunnlag(datagrunnlag: Datagrunnlag): ReglerForMedl {
            with(datagrunnlag) {
                return ReglerForMedl(
                    ytelse = ytelse,
                    regelFactory = RegelFactory(datagrunnlag),
                    overstyrteRegler = datagrunnlag.overstyrteRegler
                )
            }
        }

        private fun lagRegelMap(datagrunnlag: Datagrunnlag): Map<RegelId, Regel> {
            val regelListe = listOf(
                ErArbeidsforholdUendretRegel.fraDatagrunnlag(datagrunnlag, REGEL_1_5),
                ErBrukersDekningUavklartRegel.fraDatagrunnlag(datagrunnlag),
                ErPeriodeMedMedlemskapInnenfor12MndPeriodeRegel.fraDatagrunnlag(datagrunnlag),
                ErPerioderAvklartRegel.fraDatagrunnlag(datagrunnlag),
                ErPeriodeUtenMedlemskapInnenfor12MndPeriodeRegel.fraDatagrunnlag(datagrunnlag),
                HarBrukerDekningIMedlRegel.fraDatagrunnlag(datagrunnlag),
                PeriodeMedMedlemskapRegel.fraDatagrunnlag(datagrunnlag),
                PeriodeMedOgUtenMedlemskapRegel.fraDatagrunnlag(datagrunnlag),
                FinnesOpplysningerIGosysRegel.fraDatagrunnlag(datagrunnlag),
                FinnesOpplysningerIJoarkRegel.fraDatagrunnlag(datagrunnlag),
                FinnesOpplysningerIMedlRegel.fraDatagrunnlag(datagrunnlag),
                HarBrukerRegistrerteOpplysningerRegel.fraDatagrunnlag(datagrunnlag)
            )

            return regelListe.map { it.regelId to it.regel }.toMap()
        }
    }
}
