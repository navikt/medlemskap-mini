package no.nav.medlemskap.regler.v1.udi

import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.Oppholdstillatelse
import no.nav.medlemskap.domene.Periode
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.domene.arbeidsforhold.Arbeidsforhold
import no.nav.medlemskap.regler.common.BasisRegel
import no.nav.medlemskap.regler.common.RegelId
import no.nav.medlemskap.regler.common.Resultat
import no.nav.medlemskap.regler.common.Resultat.Companion.ja
import no.nav.medlemskap.regler.common.Resultat.Companion.nei
import no.nav.medlemskap.regler.common.Resultat.Companion.uavklart

class DekkerOppholdstillatelseArbeidsperiodeRegel(
    ytelse: Ytelse,
    private val oppholdstillatelse: Oppholdstillatelse?,
    private val arbeidsforhold: List<Arbeidsforhold>,
    regelId: RegelId = RegelId.REGEL_19_3_1
) : BasisRegel(regelId, ytelse) {

    override fun operasjon(): Resultat {

        if (oppholdstillatelse?.gjeldendeOppholdsstatus == null) {
            return uavklart(regelId)
        }

        val oppholdstillatelse = oppholdstillatelse.gjeldendeOppholdsstatus.oppholdstillatelsePaSammeVilkar

        val dekkerOppholdstillatelseArbeidsperioden = arbeidsforhold.any {
            oppholdstillatelse?.periode?.encloses(
                Periode(it.periode.fom, it.periode.tom ?: oppholdstillatelse.periode.tom)
            ) == true
        }

        return if (dekkerOppholdstillatelseArbeidsperioden) {
            ja(regelId)
        } else {
            nei(regelId)
        }
    }

    companion object {

        fun fraDatagrunnlag(datagrunnlag: Datagrunnlag): DekkerOppholdstillatelseArbeidsperiodeRegel {
            return DekkerOppholdstillatelseArbeidsperiodeRegel(
                ytelse = datagrunnlag.ytelse,
                oppholdstillatelse = datagrunnlag.oppholdstillatelse,
                arbeidsforhold = datagrunnlag.arbeidsforhold
            )
        }
    }
}
