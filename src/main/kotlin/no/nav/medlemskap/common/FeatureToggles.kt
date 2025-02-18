package no.nav.medlemskap.common

enum class FeatureToggles(val enabled: Boolean = false, private val description: String = "") {
    FEATURE_UDI(true, "Kan ikke enables før vi får til å kalle UDI-tjenesten")
}
