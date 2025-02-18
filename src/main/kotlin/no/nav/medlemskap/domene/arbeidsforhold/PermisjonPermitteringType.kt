package no.nav.medlemskap.domene.arbeidsforhold

enum class PermisjonPermitteringType(val kodeverdi: String) {
    PERMISJON("permisjon"),
    PERMISJON_MED_FORELDREPENGER("permisjonMedForeldrepenger"),
    PERMISJON_VED_MILITAERTJENESTE("permisjonVedMilitaertjeneste"),
    PERMITTERING("permittering"),
    UTDANNINGSPERMISJON("utdanningspermisjon"),
    VELFERDSPERMISJON("velferdspermisjon"),
    ANDRE_IKKE_LOVFESTEDE_PERMISJONER("andreIkkeLovfestedePermisjoner"),
    ANDRE_LOVFESTEDE_PERMISJONER("andreLovfestedePermisjoner"),
    UTDANNINGSPERMISJON_IKKE_LOVFESTET("utdanningspermisjonIkkeLovfestet"),
    UTDANNINGSPERMISJON_LOVFESTET("utdanningspermisjonLovfestet"),
    ANNET("Annet")
    ;

    companion object {
        fun fraPermisjonPermitteringVerdi(permisjonPermittering: String): PermisjonPermitteringType {
            return PermisjonPermitteringType.values().first { it.kodeverdi == permisjonPermittering }
        }
    }
}
