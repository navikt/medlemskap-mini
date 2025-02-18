package no.nav.medlemskap.services.aareg

import no.nav.medlemskap.clients.aareg.AaRegClient
import java.time.LocalDate

class ArbeidsgiverPerson(val aaRegClient: AaRegClient) {

    suspend fun getIdent(fnr: String, callId: String, fraOgMed: LocalDate? = null, tilOgMed: LocalDate? = null): List<String> {
        val opplysningspliktigArbeidsgiver =
            aaRegClient.hentArbeidsforhold(fnr, callId, fraOgMed, tilOgMed).map { it.arbeidsgiver }

        val offentligIdent: List<String> = opplysningspliktigArbeidsgiver.mapNotNull { it.offentligIdent }
        val aktoerId: List<String> = opplysningspliktigArbeidsgiver.mapNotNull { it.aktoerId }
        return offentligIdent.plus(aktoerId)
    }
}
