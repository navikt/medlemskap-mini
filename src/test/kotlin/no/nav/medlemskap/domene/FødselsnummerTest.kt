package no.nav.medlemskap.domene

import no.nav.medlemskap.domene.Fødselsnummer.Companion.gyldigFnr
import no.nav.medlemskap.domene.Fødselsnummer.Companion.hentBursdagsAar
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class FødselsnummerTest {

    @Test
    fun hentBursdagsAar() {
        assertEquals("1979", FNR_AREMARK.hentBursdagsAar())
    }

    @Test
    fun `gyldig fødslesnummer gir true i verifikasjonsmetode`() {
        assertEquals(true, gyldigFnr(FNR_AREMARK))
    }

    companion object {
        private val FNR_AREMARK = "30117942593"
    }
}
