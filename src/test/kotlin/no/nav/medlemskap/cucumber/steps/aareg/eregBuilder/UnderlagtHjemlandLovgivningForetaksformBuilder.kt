package no.nav.medlemskap.cucumber.steps.aareg.eregBuilder

import no.nav.medlemskap.clients.ereg.UnderlagtHjemlandLovgivningForetaksform

class UnderlagtHjemlandLovgivningForetaksformBuilder {
    var bruksperiodeBuilder = EregBruksperiodeBuilder()
    var gyldighetsperiodeBuilder = EregGyldighetsperiodeBuilder()

    var bruksperiode = bruksperiodeBuilder.build()
    var gyldighetsperiode = gyldighetsperiodeBuilder.build()
    var beskrivelseHjemland = String()
    var beskrivelseNorge = String()
    var foretaksform = String()
    var landkode = String()

    fun build(): UnderlagtHjemlandLovgivningForetaksform =
        UnderlagtHjemlandLovgivningForetaksform(
            beskrivelseHjemland = beskrivelseHjemland,
            beskrivelseNorge = beskrivelseNorge,
            bruksperiode = bruksperiode,
            foretaksform = foretaksform,
            gyldighetsperiode = gyldighetsperiode,
            landkode = landkode
        )
}
