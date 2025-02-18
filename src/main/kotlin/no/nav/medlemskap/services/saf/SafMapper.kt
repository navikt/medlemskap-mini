package no.nav.medlemskap.services.saf

import no.nav.medlemskap.clients.saf.generated.Dokumenter
import no.nav.medlemskap.domene.*

fun mapDokumentoversiktBrukerResponse(response: Dokumenter.Result): List<Journalpost> =
    mapJournalResultat(response.dokumentoversiktBruker.journalposter)

fun mapJournalResultat(journal: List<no.nav.medlemskap.clients.saf.generated.dokumenter.Journalpost?>): List<Journalpost> {
    return journal.filterNotNull().map {
        Journalpost(
            datoOpprettet = it.datoOpprettet,
            relevanteDatoer = mapRelevanteDatoer(it),
            dokumenter = mapDokumenter(it),
            journalpostId = it.journalpostId,
            journalfortAvNavn = it.journalfortAvNavn,
            journalposttype = it.journalposttype.toString(),
            journalstatus = it.journalstatus.toString(),
            tema = it.tema.toString(),
            sak = mapSak(it),
            tittel = it.tittel
        )
    }
}

fun mapSak(journalPost: no.nav.medlemskap.clients.saf.generated.dokumenter.Journalpost): Sak {
    return Sak(fagsakId = journalPost.sak?.fagsakId)
}

fun mapRelevanteDatoer(journalPost: no.nav.medlemskap.clients.saf.generated.dokumenter.Journalpost): List<RelevantDato> {

    return journalPost.relevanteDatoer?.filterNotNull()?.map {
        RelevantDato(it.dato, Datotype.valueOf(it.datotype.name))
    } ?: emptyList()
}
fun mapDokumenter(journalPost: no.nav.medlemskap.clients.saf.generated.dokumenter.Journalpost): List<Dokument> {
    return journalPost.dokumenter?.filterNotNull()?.map {
        Dokument(
            dokumentId = it.dokumentInfoId,
            tittel = it.tittel
        )
    } ?: emptyList()
}
