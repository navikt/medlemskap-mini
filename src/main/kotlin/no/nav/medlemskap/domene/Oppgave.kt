package no.nav.medlemskap.domene

import no.nav.medlemskap.regler.common.Funksjoner.erDelAv
import java.time.LocalDate

data class Oppgave(
    val aktivDato: LocalDate,
    val prioritet: Prioritet,
    val status: Status,
    val tema: String?
) {
    companion object {
        fun List<Oppgave>.finnesAapneOppgaver(): Boolean = this.aapneOppgaver().isNotEmpty()

        private fun List<Oppgave>.aapneOppgaver(): List<Oppgave> =
            this.filter { it.tema erDelAv tillatteTemaer && it.status erDelAv tillatteStatuser }

        private val tillatteTemaer = listOf("MED", "UFM", "TRY")
        private val tillatteStatuser = listOf(Status.AAPNET, Status.OPPRETTET, Status.UNDER_BEHANDLING)
    }
}

enum class Prioritet {
    HOY, NORM, LAV
}

enum class Status {
    OPPRETTET, AAPNET, UNDER_BEHANDLING, FERDIGSTILT, FEILREGISTRERT
}
