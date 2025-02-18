package no.nav.medlemskap.domene.personhistorikk

import java.time.LocalDate

data class UtflyttingFraNorge(
    val tilflyttingsland: String?,
    val tilflyttingsstedIUtlandet: String?,
    val utflyttingsDato: LocalDate?,
    val metadata: Metadata
)
