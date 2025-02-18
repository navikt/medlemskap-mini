package no.nav.medlemskap.regler.v1.arbeidsforhold

import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.domene.Periode
import no.nav.medlemskap.domene.Ytelse
import no.nav.medlemskap.domene.arbeidsforhold.Arbeidsforhold
import no.nav.medlemskap.domene.arbeidsforhold.Arbeidsforhold.Companion.hentAllePermisjonerSiden
import no.nav.medlemskap.domene.arbeidsforhold.Arbeidsforhold.Companion.totaltantallDagerIKontrollPeriode
import no.nav.medlemskap.regler.common.RegelId
import no.nav.medlemskap.regler.common.Resultat
import no.nav.medlemskap.regler.common.Resultat.Companion.ja
import no.nav.medlemskap.regler.common.Resultat.Companion.nei
import java.time.LocalDate

class ErSummenAvPermisjonenMerEnn30DagerSiste12Mnd(
    ytelse: Ytelse,
    private val startDatoForYtelse: LocalDate,
    private val arbeidsforhold: List<Arbeidsforhold>,
    private val kontrollperiode: Periode,
    private val fnr: String,
    regelId: RegelId = RegelId.REGEL_33,
) : ArbeidsforholdRegel(regelId, ytelse, startDatoForYtelse) {

    override fun operasjon(): Resultat {


        val allePermisjonerSomLøperForMindreEnEtÅrSiden = arbeidsforhold.hentAllePermisjonerSiden(startDatoForYtelse.minusYears(1))
        val totaltAntallDagerPermisjon = allePermisjonerSomLøperForMindreEnEtÅrSiden.totaltantallDagerIKontrollPeriode(kontrollPeriodeForArbeidsforhold)
        if (totaltAntallDagerPermisjon > 30){
            return ja(regelId)
        }
        return nei(regelId)

    }


    companion object {

        fun fraDatagrunnlag(datagrunnlag: Datagrunnlag): ErSummenAvPermisjonenMerEnn30DagerSiste12Mnd {
            return ErSummenAvPermisjonenMerEnn30DagerSiste12Mnd(
                ytelse = datagrunnlag.ytelse,
                startDatoForYtelse = datagrunnlag.startDatoForYtelse,
                arbeidsforhold = datagrunnlag.arbeidsforhold,
                kontrollperiode = Periode(datagrunnlag.periode.fom, datagrunnlag.periode.tom),
                fnr = datagrunnlag.fnr,
            )
        }
    }
}


