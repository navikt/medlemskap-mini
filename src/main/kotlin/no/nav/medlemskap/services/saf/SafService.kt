package no.nav.medlemskap.services.saf

import no.nav.medlemskap.clients.saf.SafClient

class SafService(private val safClient: SafClient) {
    suspend fun hentJournaldata(fnr: String, callId: String) =
        mapDokumentoversiktBrukerResponse(safClient.hentJournaldatav2(fnr, callId))
}
