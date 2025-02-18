package no.nav.medlemskap.regler.v1

import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.regler.common.RegelId
import no.nav.medlemskap.regler.common.RegelId.*
import no.nav.medlemskap.regler.common.Regelflyt
import no.nav.medlemskap.regler.common.Regelflyt.Companion.regelflytJa
import no.nav.medlemskap.regler.common.Regelflyt.Companion.regelflytUavklart
import no.nav.medlemskap.regler.common.Regler
import no.nav.medlemskap.regler.common.Svar

class ReglerForArbeidsforhold(
    ytelse: Ytelse,
    regelFactory: RegelFactory,
    overstyrteRegler: Map<RegelId, Svar>
) : Regler(ytelse, regelFactory, overstyrteRegler) {

    override fun hentHovedflyt(): Regelflyt {
        val jobberBrukerPaaNorskSkipFlyt = lagRegelflyt(
            regel = hentRegel(REGEL_7_1),
            hvisJa = regelflytJa(ytelse, REGEL_ARBEIDSFORHOLD),
            hvisNei = regelflytUavklart(ytelse, REGEL_ARBEIDSFORHOLD)
        )

        val erBrukerPilotEllerKabinansattFlyt = lagRegelflyt(
            regel = hentRegel(REGEL_8),
            hvisJa = regelflytUavklart(ytelse, REGEL_ARBEIDSFORHOLD),
            hvisNei = regelflytJa(ytelse, REGEL_ARBEIDSFORHOLD)
        )

        val erArbeidsforholdetMaritimtFlyt = lagRegelflyt(
            regel = hentRegel(REGEL_7),
            hvisJa = jobberBrukerPaaNorskSkipFlyt,
            hvisNei = erBrukerPilotEllerKabinansattFlyt
        )


        val harForetakMerEnn5AnsatteFlyt = lagRegelflyt(
            regel = hentRegel(REGEL_5),
            hvisJa = erArbeidsforholdetMaritimtFlyt,
            hvisNei = regelflytUavklart(ytelse, REGEL_ARBEIDSFORHOLD)
        )

        val erArbeidsgiverOffentligSektor = lagRegelflyt(
            regel = hentRegel(REGEL_14),
            hvisJa = regelflytJa(ytelse, REGEL_ARBEIDSFORHOLD),
            hvisNei = harForetakMerEnn5AnsatteFlyt
        )


        return erArbeidsgiverOffentligSektor
    }

    companion object {
        fun fraDatagrunnlag(
            datagrunnlag: Datagrunnlag,
            overstyrteRegler: Map<RegelId, Svar> = emptyMap()
        ): ReglerForArbeidsforhold {
            return ReglerForArbeidsforhold(
                ytelse = datagrunnlag.ytelse,
                regelFactory = RegelFactory(datagrunnlag),
                overstyrteRegler = overstyrteRegler
            )
        }
    }
}
