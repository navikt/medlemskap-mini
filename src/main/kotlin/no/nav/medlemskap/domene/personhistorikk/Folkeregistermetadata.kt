package no.nav.medlemskap.domene.personhistorikk

import java.time.LocalDateTime

data class Folkeregistermetadata(
    val ajourholdstidspunkt: LocalDateTime?,
    val gyldighetstidspunkt: LocalDateTime?,
    val opphoerstidspunkt: LocalDateTime?,
    val aarsak: String?
)
