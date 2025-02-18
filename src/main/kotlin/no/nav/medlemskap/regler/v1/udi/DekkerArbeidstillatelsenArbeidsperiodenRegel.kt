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

class DekkerArbeidstillatelsenArbeidsperiodenRegel(
    ytelse: Ytelse,
    private val oppholdstillatelse: Oppholdstillatelse?,
    private val arbeidsforhold: List<Arbeidsforhold>,
    regelId: RegelId = RegelId.REGEL_19_6_1
) : BasisRegel(regelId, ytelse) {

    override fun operasjon(): Resultat {

        if (oppholdstillatelse?.arbeidsadgang == null) {
            return uavklart(regelId)
        }

        val arbeidsadgang = oppholdstillatelse.arbeidsadgang

        val dekkerArbeidstillatelsenArbeidsperioden = arbeidsforhold.any {
            arbeidsadgang.periode.encloses(
                Periode(it.periode.fom, it.periode.tom ?: arbeidsadgang.periode.tom)
            )
        }

        return if (dekkerArbeidstillatelsenArbeidsperioden) {
            ja(regelId)
        } else {
            nei(regelId)
        }
    }

    companion object {

        fun fraDatagrunnlag(datagrunnlag: Datagrunnlag): DekkerArbeidstillatelsenArbeidsperiodenRegel {
            return DekkerArbeidstillatelsenArbeidsperiodenRegel(
                ytelse = datagrunnlag.ytelse,
                oppholdstillatelse = datagrunnlag.oppholdstillatelse,
                arbeidsforhold = datagrunnlag.arbeidsforhold
            )
        }
    }
}
