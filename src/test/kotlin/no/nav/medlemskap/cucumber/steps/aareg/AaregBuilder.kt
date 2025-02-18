import no.nav.medlemskap.clients.ereg.*
import no.nav.medlemskap.cucumber.steps.aareg.aaregBuilder.AaRegArbeidsforholdBuilder
import no.nav.medlemskap.cucumber.steps.aareg.eregBuilder.OrganisasjonBuilder
import no.nav.medlemskap.services.aareg.ArbeidsforholdOrganisasjon

class AaregBuilder() {
    var organisasjonsBuilder = OrganisasjonBuilder()

    var arbeidsforhold = AaRegArbeidsforholdBuilder().build()
    var organisasjon = organisasjonsBuilder.build()
    var juridiskEnhet = mutableListOf<Organisasjon>(organisasjonsBuilder.build())

    fun build(): List<ArbeidsforholdOrganisasjon> {
        return listOf(
            ArbeidsforholdOrganisasjon(
                arbeidsforhold = arbeidsforhold,
                organisasjon = organisasjon,
                juridiskeEnheter = juridiskEnhet
            )
        )
    }
}
