package no.nav.medlemskap.common

data class Versjon(
    val versjon: String,
    val default: Boolean,
    val beskrivelse: String
)

fun hentVersjoner(): List<Versjon> = listOf(
    Versjon(
        versjon = "v1",
        default = true,
        beskrivelse =
            """
                    Denne versjonen dekker EØS-forordning (EF 883/2004) samt Kapittel 2 i Lov om
                    Folketrygd (LOV-1997-02-28-19)
            """.trimIndent()
    )
)
