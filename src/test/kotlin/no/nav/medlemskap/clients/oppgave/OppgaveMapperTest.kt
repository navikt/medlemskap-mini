package no.nav.medlemskap.clients.oppgave

import com.fasterxml.jackson.module.kotlin.readValue
import no.nav.medlemskap.clients.saf.generated.enums.Tema
import no.nav.medlemskap.common.objectMapper
import no.nav.medlemskap.domene.Prioritet
import no.nav.medlemskap.domene.Status
import no.nav.medlemskap.services.oppgave.mapOppgaveResultat
import org.junit.Assert
import org.junit.jupiter.api.Test
import java.time.LocalDate

class OppgaveMapperTest {

    @Test
    fun mapOppgaveResultatTilOppgave() {

        val oppgaverResponse = objectMapper.readValue<FinnOppgaverResponse>(oppgaveResponse)
        val mappedOppgaveResultat = mapOppgaveResultat(oppgaverResponse.oppgaver)

        Assert.assertEquals(1, mappedOppgaveResultat.size)
        Assert.assertEquals(LocalDate.parse("2010-01-01"), mappedOppgaveResultat.first().aktivDato)
        Assert.assertEquals(Prioritet.HOY, mappedOppgaveResultat.first().prioritet)
        Assert.assertEquals(Status.AAPNET, mappedOppgaveResultat.first().status)
        Assert.assertEquals(Tema.MED.name, mappedOppgaveResultat.first().tema)
    }

    val oppgaveResponse =
        """
        {
            "antallTreffTotalt": 1,
            "oppgaver": [
                {
                    "aktivDato": "2010-01-01",
                    "prioritet": "HOY",
                    "status": "AAPNET",
                    "versjon": 1,
                    "tilordnetRessurs": "Z000001",
                    "tema": "MED",
                    "beskrivelse": "Testbeskrivelse",
                    "shouldBeIgnored": "ign"
                }
            ]
        }
        """.trimIndent()
}
