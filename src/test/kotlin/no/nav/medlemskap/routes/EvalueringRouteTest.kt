package no.nav.medlemskap.routes

import no.nav.medlemskap.regler.common.Svar
import no.nav.medlemskap.regler.personer.Personleser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class EvalueringRouteTest {

    @Test
    fun `tester kjøring av hele routen`() {

        val resultat = evaluerData(Personleser.brukerIkkeFolkeregistrertSomBosattINorge())
        assertEquals(Svar.UAVKLART, resultat.svar)
    }
}
