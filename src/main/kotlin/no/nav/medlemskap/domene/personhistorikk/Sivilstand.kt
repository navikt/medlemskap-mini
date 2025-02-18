package no.nav.medlemskap.domene.personhistorikk

import java.time.LocalDate

data class Sivilstand(
    val type: Sivilstandstype,
    val gyldigFraOgMed: LocalDate?,
    val gyldigTilOgMed: LocalDate?,
    val relatertVedSivilstand: String?
)
