package no.nav.medlemskap.domene.arbeidsforhold

import no.nav.medlemskap.domene.Periode

data class Arbeidsavtale(
    var periode: Periode,
    val gyldighetsperiode: Periode,
    val yrkeskode: String,
    val skipsregister: Skipsregister?,
    val fartsomraade: Fartsomraade?,
    val stillingsprosent: Double?,
    val beregnetAntallTimerPrUke: Double?,
    val skipstype: Skipstype?
) {
    fun getStillingsprosent(): Double {
        if (stillingsprosent == 0.0 && beregnetAntallTimerPrUke != null && beregnetAntallTimerPrUke > 0) {
            val beregnetStillingsprosent = (beregnetAntallTimerPrUke / 37.5) * 100
            return Math.round(beregnetStillingsprosent * 10.0) / 10.0
        }

        return stillingsprosent ?: 100.0
    }
}
