package no.nav.medlemskap.domene.arbeidsforhold

enum class Fartsomraade(val beskrivelse: String) {
    INNENRIKS("innenriks"),
    UTENRIKS("utenriks");

    companion object {
        fun fraFartsomraadeVerdi(fartsomradeValue: String?): Fartsomraade? {
            if (fartsomradeValue.isNullOrEmpty()) return null
            return valueOf(fartsomradeValue.toUpperCase())
        }
    }
}
