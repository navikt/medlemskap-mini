package no.nav.medlemskap.regler.v1

import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.InputPeriode
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.regler.common.*
import no.nav.medlemskap.regler.common.RegelId.*
import no.nav.medlemskap.regler.common.Regelflyt.Companion.konklusjonUavklart
import no.nav.medlemskap.regler.common.Regelflyt.Companion.regelflytJa

class ReglerForOppholdstillatelse(
    val periode: InputPeriode,
    ytelse: Ytelse,
    regelFactory: RegelFactory,
    overstyrteRegler: Map<RegelId, Svar>
) : Regler(ytelse, regelFactory, overstyrteRegler) {

    override fun hentHovedflyt(): Regelflyt {

        val dekkerArbeidstillatelsenArbeidsperiodenRegelflyt = lagRegelflyt(
            regel = hentRegel(REGEL_19_6_1),
            hvisJa = regelflytJa(ytelse, REGEL_OPPHOLDSTILLATELSE),
            hvisNei = konklusjonUavklart(ytelse, REGEL_OPPHOLDSTILLATELSE)
        )

        val harBrukerGyldigArbeidstillatelseIKontrollperiodeRegelflyt = lagRegelflyt(
            regel = hentRegel(REGEL_19_6),
            hvisJa = regelflytJa(ytelse, REGEL_OPPHOLDSTILLATELSE),
            hvisNei = dekkerArbeidstillatelsenArbeidsperiodenRegelflyt
        )

        val erArbeidsadgangUavklartRegelFlyt = lagRegelflyt(
            regel = hentRegel(REGEL_19_5),
            hvisJa = konklusjonUavklart(ytelse, REGEL_OPPHOLDSTILLATELSE),
            hvisNei = harBrukerGyldigArbeidstillatelseIKontrollperiodeRegelflyt
        )

        val harBrukerPermanentOppholdstillatelse = lagRegelflyt(
            regel = hentRegel(REGEL_31),
            hvisJa = regelflytJa(ytelse, REGEL_OPPHOLDSTILLATELSE),
            hvisNei = erArbeidsadgangUavklartRegelFlyt
        )

        val erBrukerBritiskEllerSveitsiskBorgerRegelflyt = lagRegelflyt(
            regel = hentRegel(REGEL_19_7),
            hvisJa = konklusjonUavklart(ytelse, REGEL_OPPHOLDSTILLATELSE),
            hvisNei = konklusjonUavklart(ytelse, REGEL_OPPHOLDSTILLATELSE),
            årsak = Årsak(REGEL_19_3_1, REGEL_19_3_1.avklaring, Svar.UAVKLART)
        )

        val dekkerOppholdstillatelseArbeidsperiodeRegel = lagRegelflyt(
            regel = hentRegel(REGEL_19_3_1),
            hvisJa = erArbeidsadgangUavklartRegelFlyt,
            hvisNei = erBrukerBritiskEllerSveitsiskBorgerRegelflyt
        )

        val harBrukerGyldigOppholdstillatelseIKontrollperiodeRegelflyt = lagRegelflyt(
            regel = hentRegel(REGEL_19_3),
            hvisJa = harBrukerPermanentOppholdstillatelse,
            hvisNei = dekkerOppholdstillatelseArbeidsperiodeRegel,
            hvisUavklart = konklusjonUavklart(ytelse, REGEL_OPPHOLDSTILLATELSE)
        )

        val harBrukerEOSellerEFTAOppholdOgBritiskEktefelleRegelflyt = lagRegelflyt(
            regel = hentRegel(REGEL_19_4),
            hvisJa = konklusjonUavklart(ytelse, REGEL_OPPHOLDSTILLATELSE),
            hvisNei = harBrukerGyldigOppholdstillatelseIKontrollperiodeRegelflyt
        )

        val harBritiskBrukerEOSellerEFTAOppholdRegelFlyt = lagRegelflyt(
            regel = hentRegel(REGEL_30),
            hvisJa = konklusjonUavklart(ytelse, REGEL_OPPHOLDSTILLATELSE),
            hvisNei = harBrukerEOSellerEFTAOppholdOgBritiskEktefelleRegelflyt
        )

        val harIkkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisumFlyt = lagRegelflyt(
            regel = hentRegel(REGEL_23),
            hvisJa = konklusjonUavklart(ytelse, REGEL_OPPHOLDSTILLATELSE),
            hvisNei = harBritiskBrukerEOSellerEFTAOppholdRegelFlyt
        )

        val harBrukerFlereOppholdstillatelserSomOverlapper = lagRegelflyt(
            regel = hentRegel(REGEL_19_2),
            hvisJa = konklusjonUavklart(ytelse, REGEL_OPPHOLDSTILLATELSE),
            hvisNei = harIkkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisumFlyt
        )

        val harBrukerOppholdPaSammeVilkarFlagg = lagRegelflyt(
            regel = hentRegel(REGEL_19_8),
            hvisJa = konklusjonUavklart(ytelse, REGEL_OPPHOLDSTILLATELSE),
            hvisNei = harBrukerFlereOppholdstillatelserSomOverlapper
        )

        val erOppholdstillatelseUavklartRegelflyt = lagRegelflyt(
            regel = hentRegel(REGEL_19_1),
            hvisJa = konklusjonUavklart(ytelse, REGEL_OPPHOLDSTILLATELSE),
            hvisNei = harBrukerOppholdPaSammeVilkarFlagg
        )

        return erOppholdstillatelseUavklartRegelflyt
    }

    companion object {
        fun fraDatagrunnlag(datagrunnlag: Datagrunnlag): ReglerForOppholdstillatelse {
            with(datagrunnlag) {
                return ReglerForOppholdstillatelse(
                    periode = periode,
                    ytelse = ytelse,
                    regelFactory = RegelFactory(datagrunnlag),
                    overstyrteRegler = overstyrteRegler
                )
            }
        }
    }
}
