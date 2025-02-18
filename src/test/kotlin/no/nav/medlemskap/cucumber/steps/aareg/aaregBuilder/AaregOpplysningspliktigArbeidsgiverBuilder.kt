package no.nav.medlemskap.cucumber.steps.aareg.aaregBuilder

import no.nav.medlemskap.clients.aareg.AaRegOpplysningspliktigArbeidsgiver
import no.nav.medlemskap.clients.aareg.AaRegOpplysningspliktigArbeidsgiverType

class AaregOpplysningspliktigArbeidsgiverBuilder {
    var type = AaRegOpplysningspliktigArbeidsgiverType.Organisasjon
    var organisasjonsnummer = String()
    var aktoerId = String()
    var offentligIdent = String()

    fun build(): AaRegOpplysningspliktigArbeidsgiver =
        AaRegOpplysningspliktigArbeidsgiver(
            type = type,
            organisasjonsnummer = organisasjonsnummer,
            aktoerId = aktoerId,
            offentligIdent = offentligIdent
        )
}
