package no.nav.medlemskap.regler.v1.arbeidsforhold

import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.Periode
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.domene.arbeidsforhold.Arbeidsforhold
import no.nav.medlemskap.domene.arbeidsforhold.Arbeidsforhold.Companion.finnOverlappendePermisjoner
import no.nav.medlemskap.domene.arbeidsforhold.Arbeidsforhold.Companion.harNoenArbeidsforhold100ProsentPermisjonIKontrollPerioden
import no.nav.medlemskap.domene.arbeidsforhold.Arbeidsforhold.Companion.harPermisjonerSiste12Måneder
import no.nav.medlemskap.regler.common.RegelId
import no.nav.medlemskap.regler.common.Resultat
import no.nav.medlemskap.regler.common.Resultat.Companion.ja
import no.nav.medlemskap.regler.common.Resultat.Companion.nei
import java.time.LocalDate


class HarBrukerPermisjonSiste12Måneder(
    ytelse: Ytelse,
    private val startDatoForYtelse: LocalDate,
    private val arbeidsforhold: List<Arbeidsforhold>,
    regelId: RegelId = RegelId.REGEL_32,
) : ArbeidsforholdRegel(regelId, ytelse, startDatoForYtelse) {

    override fun operasjon(): Resultat {
        if (harPermisjonerSiste12Måneder(arbeidsforhold, kontrollPeriodeForArbeidsforhold)) {
            return ja(regelId)
        }
        return nei(regelId)
    }


    companion object {

        fun fraDatagrunnlag(datagrunnlag: Datagrunnlag): HarBrukerPermisjonSiste12Måneder {
            return HarBrukerPermisjonSiste12Måneder(
                ytelse = datagrunnlag.ytelse,
                startDatoForYtelse = datagrunnlag.startDatoForYtelse,
                arbeidsforhold = datagrunnlag.arbeidsforhold
            )
        }
    }
}


