package no.nav.medlemskap.domene.arbeidsforhold

enum class Skipstype {
    ANNET,
    BOREPLATTFORM,
    TURIST;

    companion object {
        fun fraSkipstypeVerdi(skipstypeValue: String?): Skipstype? {
            if (skipstypeValue.isNullOrEmpty()) return null
            return valueOf(skipstypeValue.toUpperCase())
        }
    }
}
