package no.nav.medlemskap.cucumber.steps.oppgave

import io.cucumber.datatable.DataTable
import io.cucumber.java8.No
import io.kotest.matchers.shouldBe
import no.nav.medlemskap.clients.oppgave.OppgOppgave
import no.nav.medlemskap.clients.oppgave.OppgPrioritet
import no.nav.medlemskap.clients.oppgave.OppgStatus
import no.nav.medlemskap.cucumber.SpraakParserDomene.OppgaverDomeneSpraakParser
import no.nav.medlemskap.cucumber.mapping.oppgave.OppgaveDomeneSpraakParser
import no.nav.medlemskap.domene.Oppgave
import no.nav.medlemskap.services.oppgave.mapOppgaveResultat
import java.time.LocalDate

class OppgaveMapperSteps : No {
    private val oppgaveDomenespråkParser = OppgaveDomeneSpraakParser()
    private var oppgaveBuilder = OppgaveBuilder()
    private var oppgaver = listOf<Oppgave>()

    init {
        Gitt("følgende om aktivDato i OppgOppgave") { dataTable: DataTable ->
            oppgaveBuilder.aktivDato = oppgaveDomenespråkParser.mapAktivDato(dataTable)
        }

        Gitt("følgende prioritet fra OppgOppgave") { dataTable: DataTable ->
            oppgaveBuilder.prioritet = oppgaveDomenespråkParser.mapPrioritet(dataTable)
        }

        Gitt("følgende Status fra OppgOppgave") { dataTable: DataTable ->
            oppgaveBuilder.status = oppgaveDomenespråkParser.mapStatus(dataTable)
        }

        Gitt("følgende tema fra OppgOppgave") { dataTable: DataTable ->
            oppgaveBuilder.tema = oppgaveDomenespråkParser.mapTema(dataTable)
        }

        Når("oppgaver mappes") {
            oppgaver = mapTilOppgave()
        }

        Så("skal mappede  aktivDato i medlemskap domene være") { dataTable: DataTable ->
            val aktivDatoForventet = OppgaverDomeneSpraakParser.mapAktivDato(dataTable)
            oppgaver[0].aktivDato.shouldBe(aktivDatoForventet)
        }

        Så("skal prioritet i Oppgave domene være") { dataTable: DataTable ->
            val prioritetForventet = OppgaverDomeneSpraakParser.mapOppgavePrioritet(dataTable)
            oppgaver[0].prioritet.shouldBe(prioritetForventet)
        }

        Så("skal status i Oppgave domene være") { dataTable: DataTable ->
            val statusForventet = OppgaverDomeneSpraakParser.mapOppgaveStatus(dataTable)
            oppgaver[0].status.shouldBe(statusForventet)
        }

        Så("skal mappede OppgOppgave være") { dataTable: DataTable ->
            val temaForventet = OppgaverDomeneSpraakParser.mapOppgaveTema(dataTable)
            oppgaver[0].tema.shouldBe(temaForventet)
        }
    }

    private fun mapTilOppgave(): List<Oppgave> {
        return mapOppgaveResultat(listOf(oppgaveBuilder.build()))
    }

    class OppgaveBuilder {
        var aktivDato = LocalDate.now()
        var prioritet = OppgPrioritet.HOY
        var status = OppgStatus.AAPNET
        var versjon = 1
        var tilordnetRessurs = String()
        var tema = String()
        var beskrivelse = String()

        fun build(): OppgOppgave {
            return OppgOppgave(
                aktivDato = aktivDato,
                prioritet = prioritet,
                status = status,
                versjon = versjon,
                tilordnetRessurs = tilordnetRessurs,
                tema = tema,
                beskrivelse = beskrivelse
            )
        }
    }
}
