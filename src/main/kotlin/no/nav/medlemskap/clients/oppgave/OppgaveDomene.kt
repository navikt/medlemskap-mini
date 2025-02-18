package no.nav.medlemskap.clients.oppgave

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.time.LocalDate

@JsonIgnoreProperties(ignoreUnknown = true)
data class FinnOppgaverResponse(
    val antallTreffTotalt: Int,
    val oppgaver: List<OppgOppgave>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class OppgOppgave(
    val aktivDato: LocalDate,
    val prioritet: OppgPrioritet,
    val status: OppgStatus,
    val versjon: Int,
    val tilordnetRessurs: String?,
    val tema: String?,
    val beskrivelse: String?
)

enum class OppgPrioritet {
    HOY, NORM, LAV
}

enum class OppgStatus {
    OPPRETTET, AAPNET, UNDER_BEHANDLING, FERDIGSTILT, FEILREGISTRERT
}
