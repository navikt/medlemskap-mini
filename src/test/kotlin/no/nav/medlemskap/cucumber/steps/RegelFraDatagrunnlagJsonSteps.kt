package no.nav.medlemskap.cucumber.steps

import io.cucumber.java8.No
import junit.framework.TestCase.assertEquals
import no.nav.medlemskap.cucumber.DomenespråkParser
import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.regler.assertDelresultat
import no.nav.medlemskap.regler.common.RegelId.REGEL_2
import no.nav.medlemskap.regler.common.Resultat
import no.nav.medlemskap.regler.personer.Personleser
import no.nav.medlemskap.regler.v1.ReglerService

class RegelFraDatagrunnlagJsonSteps : No {
    private var resultat: Resultat? = null
    private var datagrunnlag: Datagrunnlag? = null
    private val domenespråkParser = DomenespråkParser

    init {
        Gitt("følgende datagrunnlag json") { docString: String? ->
            datagrunnlag = Personleser.dataGrunnlagFraJson(docString!!)
        }

        Når("lovvalg og medlemskap beregnes fra datagrunnlag json") {
            resultat = ReglerService.kjørRegler(datagrunnlag!!)
        }

        Så("skal delresultat {string} være {string}") { regelIdStr: String, forventetSvar: String? ->
            val regelId = domenespråkParser.parseRegelId(regelIdStr)

            assertDelresultat(regelId, domenespråkParser.parseSvar(forventetSvar!!), resultat!!)
        }

        Så("omfattet av grunnforordningen være {string}") { forventetSvar: String? ->
            assertDelresultat(REGEL_2, domenespråkParser.parseSvar(forventetSvar!!), resultat!!)
        }

        Så("skal medlemskap i Folketrygden være {string}") {
            forventetVerdi: String ->
            val forventetSvar = domenespråkParser.parseSvar(forventetVerdi)
            assertEquals(forventetSvar, resultat!!.svar)
        }
    }
}
