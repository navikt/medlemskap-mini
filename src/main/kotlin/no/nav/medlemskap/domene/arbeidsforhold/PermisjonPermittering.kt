package no.nav.medlemskap.domene.arbeidsforhold

import no.nav.medlemskap.domene.Periode

data class PermisjonPermittering(
    val periode: Periode,
    val permisjonPermitteringId: String,
    val prosent: Double?,
    val type: PermisjonPermitteringType,
    val varslingskode: String?
)
