package no.nav.medlemskap.domene.personhistorikk

import java.time.LocalDate
import java.time.LocalDateTime

data class Opphold(
    val type: OppholdstillatelseType,
    val oppholdFra: LocalDate?,
    val oppholdTil: LocalDate?,
    val medtadata: OppholdMetadata,
    val folkeregistermetadata: Folkeregistermetadata
)

enum class OppholdstillatelseType {
    MIDLERTIDIG,
    PERMANENT,
    OPPLYSNING_MANGLER,
    __UNKNOWN_VALUE
}

data class OppholdMetadata(
    val historisk: Boolean,
    val master: String,
    val endringer: List<Endring>
)

data class Endring(
    val type: Endringstype,
    val kilde: String,
    val registrert: LocalDateTime,
    val registrertAv: String,
    val systemkilde: String

)
enum class Endringstype {
    OPPRETT,
    KORRIGER,
    OPPHOER,
    /**
     * This is a default enum value that will be used when attempting to deserialize unknown value.
     */
    __UNKNOWN_VALUE,
}
