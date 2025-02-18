package no.nav.medlemskap.domene.arbeidsforhold

import no.nav.medlemskap.domene.Periode
import java.time.YearMonth

data class Utenlandsopphold(
    val landkode: String,
    val periode: Periode?,
    val rapporteringsperiode: YearMonth
)
