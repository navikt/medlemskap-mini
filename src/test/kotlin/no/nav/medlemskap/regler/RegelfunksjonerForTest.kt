package no.nav.medlemskap.regler

import no.nav.medlemskap.domene.Datagrunnlag
import no.nav.medlemskap.regler.common.RegelId
import no.nav.medlemskap.regler.common.Resultat
import no.nav.medlemskap.regler.common.Svar
import no.nav.medlemskap.regler.v1.Hovedregler
import org.junit.jupiter.api.Assertions

fun evaluer(datagrunnlag: Datagrunnlag): Resultat {
    val regelsett = Hovedregler(datagrunnlag)
    return regelsett.kjørHovedregler()
}

fun assertSvar(regelId: RegelId, forventetSvarFraRegel: Svar, resultat: Resultat, konklusjon: Svar) {
    val find = resultat.delresultat.find { it.regelId == regelId }

    Assertions.assertNotNull(find, "Fant ikke regel $regelId i delsvar i Resultat. Regel det testes på ble ikke kjørt. Følgende regler ble kjørt: " + resultat.delresultat.map { it.regelId })
    Assertions.assertEquals(forventetSvarFraRegel, find!!.svar, "Fikk feil svar regel: $regelId")
    Assertions.assertEquals(konklusjon, resultat.svar)
}

fun assertDelresultat(regelId: RegelId, forventetSvarFraRegel: Svar, resultat: Resultat) {
    val funnetResultat = resultat.finnRegelResultat(regelId)

    Assertions.assertNotNull(funnetResultat, "Fant ikke regel $regelId i delsvar i Resultat. Regel det testes på ble ikke kjørt. Følgende regler ble kjørt: " + resultat.delresultat.map { it.regelId })
    Assertions.assertEquals(forventetSvarFraRegel, funnetResultat!!.svar, "Fikk feil svar regel: $regelId")
}

fun assertBegrunnelse(regelId: RegelId, forventetBegrunnelseFraRegel: String, resultat: Resultat) {
    val funnetResultat = resultat.finnRegelResultat(regelId)

    Assertions.assertNotNull(funnetResultat, "Fant ikke regel $regelId i delsvar i Resultat. Regel det testes på ble ikke kjørt. Følgende regler ble kjørt: " + resultat.delresultat.map { it.regelId })
    Assertions.assertEquals(forventetBegrunnelseFraRegel, funnetResultat!!.begrunnelse, "Fikk feil begrunnelse på regel: $regelId")
}
