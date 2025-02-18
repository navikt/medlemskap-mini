package no.nav.medlemskap.domene.arbeidsforhold

data class Arbeidsgiver(
    val navn: String?,
    val organisasjonsnummer: String?,
    val ansatte: List<Ansatte>?,
    val konkursStatus: List<String?>?,
    val juridiskeEnheter: List<JuridiskEnhet?>?
)
