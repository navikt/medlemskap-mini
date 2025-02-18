package no.nav.medlemskap.services.oppgave

import no.nav.medlemskap.clients.oppgave.OppgOppgave
import no.nav.medlemskap.clients.oppgave.OppgPrioritet
import no.nav.medlemskap.clients.oppgave.OppgStatus
import no.nav.medlemskap.domene.Oppgave
import no.nav.medlemskap.domene.Prioritet
import no.nav.medlemskap.domene.Status

fun mapOppgaveResultat(oppgaver: List<OppgOppgave>): List<Oppgave> {
    return oppgaver.map {
        Oppgave(
            aktivDato = it.aktivDato,
            prioritet = mapPrioritert(it),
            status = mapStatus(it),
            tema = it.tema
        )
    }
}

fun mapStatus(oppgave: OppgOppgave): Status {
    return when (oppgave.status) {
        OppgStatus.OPPRETTET -> Status.OPPRETTET
        OppgStatus.AAPNET -> Status.AAPNET
        OppgStatus.UNDER_BEHANDLING -> Status.UNDER_BEHANDLING
        OppgStatus.FERDIGSTILT -> Status.FERDIGSTILT
        else -> {
            Status.FEILREGISTRERT
        }
    }
}

fun mapPrioritert(oppgave: OppgOppgave): Prioritet {
    return when (oppgave.prioritet) {
        OppgPrioritet.HOY -> Prioritet.HOY
        OppgPrioritet.LAV -> Prioritet.LAV
        else -> {
            Prioritet.NORM
        }
    }
}
