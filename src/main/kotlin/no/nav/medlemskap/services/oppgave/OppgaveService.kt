package no.nav.medlemskap.services.oppgave

import no.nav.medlemskap.clients.oppgave.OppgaveClient

class OppgaveService(private val oppgaveClient: OppgaveClient) {

    suspend fun hentOppgaver(aktorIder: List<String>, callId: String) =
        mapOppgaveResultat(oppgaveClient.hentOppgaver(aktorIder, callId).oppgaver)
}
