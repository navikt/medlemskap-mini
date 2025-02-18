package no.nav.medlemskap.domene

enum class Landkode(val landkode: String, val erEøsland: Boolean) {
    BEL("BELGIA", true),
    BGR("BULGARIA", true),
    DNK("DANMARK", true),
    EST("ESTLAND", true),
    FIN("FINLAND", true),
    FRA("FRANKRIKE", true),
    GRC("HELLAS", true),
    IRL("IRLAND", true),
    ISL("ISLAND", true),
    ITA("ITALIA", true),
    HRV("KROATIA", true),
    CYP("KYPROS", true),
    LVA("LATVIA", true),
    LIE("LIECHTENSTEIN", true),
    LTU("LITAUEN", true),
    LUX("LUXEMBOURG", true),
    MLT("MALTA", true),
    NLD("NEDERLAND", true),
    NOR("NORGE", true),
    POL("POLEN", true),
    PRT("PORTUGAL", true),
    ROU("ROMANIA", true),
    SVK("SLOVAKIA", true),
    SVN("SLOVENIA", true),
    ESP("SPANIA", true),
    SWE("SVERIGE", true),
    CZE("TSJEKKIA", true),
    DEU("TYSKLAND", true),
    HUN("UNGARN", true),
    AUT("ØSTERRIKE", true),
    CHE("SVEITS", true),
    GBR("STORBRITANNIA", false);

    companion object {
        fun erEØSland(landkode: String): Boolean {
            return values()
                .filter { it.erEøsland }
                .any { it.name == landkode }
        }

        fun erNorsk(landkode: String): Boolean {
            return landkode == "NOR"
        }

        fun erNordisk(landkode: String): Boolean {
            return nordiskeLandkoder.contains(landkode)
        }

        fun erSveitsisk(landkode: String): Boolean {
            return landkode == "CHE"
        }

        fun erBritisk(landkode: String): Boolean {
            return landkode == "GBR"
        }

        private val nordiskeLandkoder = setOf(
            "DNK",
            "FIN",
            "ISL",
            "SWE",
            "NOR",
            "FRO",
            "GRL",
            "ALA"
        )
    }
}
