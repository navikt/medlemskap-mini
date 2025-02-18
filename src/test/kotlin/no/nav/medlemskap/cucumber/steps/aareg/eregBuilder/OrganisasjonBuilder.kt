package no.nav.medlemskap.cucumber.steps.aareg.eregBuilder

import no.nav.medlemskap.clients.ereg.BestaarAvOrganisasjonsledd
import no.nav.medlemskap.clients.ereg.JuridiskEnhet
import no.nav.medlemskap.clients.ereg.Organisasjon

class OrganisasjonBuilder {
    var originasjonsdetaljerBuilder = OrganisasjonsDetaljerBuilder()
    var navnBuilder = NavnBuilder()

    var navn = navnBuilder.build()
    var organisjonsnummer = String()
    var type = String()
    var bestaarAvOrganisasjonsledd = mutableListOf<BestaarAvOrganisasjonsledd>()
    var inngarIJuridiskEnhet = mutableListOf<JuridiskEnhet>()
    var originasjonsDetaljer = originasjonsdetaljerBuilder.build()

    fun build(): Organisasjon =
        Organisasjon(
            navn = navn,
            organisasjonsnummer = organisjonsnummer,
            type = type,
            bestaarAvOrganisasjonsledd = bestaarAvOrganisasjonsledd,
            inngaarIJuridiskEnheter = inngarIJuridiskEnhet,
            organisasjonDetaljer = originasjonsDetaljer
        )
}
