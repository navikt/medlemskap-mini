package no.nav.medlemskap.domene.personhistorikk

data class InnflyttingTilNorge(
    val fraflyttingsland: String?,
    val fraflyttingsstedIUtlandet: String?,
    val folkeregistermetadata: Folkeregistermetadata,
    val metadata: Metadata
)
