package no.nav.medlemskap.cucumber.steps.aareg.eregBuilder

import no.nav.medlemskap.clients.ereg.JuridiskEnhet

class JuridiskEnhetBuilder {
    var organisasjonsnummer = String()
    fun build(): JuridiskEnhet =
        JuridiskEnhet(
            organisasjonsnummer = organisasjonsnummer
        )
}
