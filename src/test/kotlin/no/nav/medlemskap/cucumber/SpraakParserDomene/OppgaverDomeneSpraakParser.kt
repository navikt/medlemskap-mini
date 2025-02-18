package no.nav.medlemskap.cucumber.SpraakParserDomene

import io.cucumber.datatable.DataTable
import no.nav.medlemskap.cucumber.*
import no.nav.medlemskap.domene.Oppgave
import no.nav.medlemskap.domene.Prioritet
import no.nav.medlemskap.domene.Status
import java.time.LocalDate

object OppgaverDomeneSpraakParser : BasisDomeneParser() {

    fun mapOppgavePrioritet(dataTable: DataTable): Prioritet {
        return mapDataTable(dataTable, PrioritetMapper())[0]
    }

    fun mapAktivDato(dataTable: DataTable): LocalDate {
        return mapDataTable(dataTable, AktivDatoMapper())[0]
    }

    fun mapOppgaveTema(dataTable: DataTable): String {
        return mapDataTable(dataTable, TemaMapper())[0]
    }

    fun mapOppgaveStatus(dataTable: DataTable): Status {
        return mapDataTable(dataTable, OppgaveStatusMapper())[0]
    }

    fun mapOppgaverFraGosys(dataTable: DataTable): List<Oppgave> {
        return mapDataTable(dataTable, OppgaveMapper())
    }

    class AktivDatoMapper() : RadMapper<LocalDate> {
        override fun mapRad(rad: Map<String, String>): LocalDate {
            return parseDato(OppgaveDomenebegrep.AKTIV_DATO, rad)
        }
    }

    class OppgaveStatusMapper : RadMapper<Status> {
        override fun mapRad(rad: Map<String, String>): Status {
            return Status.valueOf(parseString(OppgaveDomenebegrep.STATUS, rad))
        }
    }

    class PrioritetMapper() : RadMapper<Prioritet> {
        override fun mapRad(rad: Map<String, String>): Prioritet {
            return Prioritet.valueOf(parseString(OppgaveDomenebegrep.PRIORITET, rad))
        }
    }

    class OppgaveMapper : RadMapper<Oppgave> {
        override fun mapRad(rad: Map<String, String>): Oppgave {
            return Oppgave(
                aktivDato = parseDato(OppgaveDomenebegrep.AKTIV_DATO, rad),
                prioritet = DomenespråkParser.parsePrioritet(OppgaveDomenebegrep.PRIORITET, rad),
                status = DomenespråkParser.parseStatus(Domenebegrep.STATUS, rad),
                tema = parseValgfriString(OppgaveDomenebegrep.TEMA, rad)
            )
        }
    }

    class TemaMapper : RadMapper<String> {
        override fun mapRad(rad: Map<String, String>): String {
            return parseString(OppgaveDomenebegrep.TEMA, rad)
        }
    }
}

enum class OppgaveDomenebegrep(val nøkkel: String) : Domenenøkkel {
    BOSTED("Bosted"),
    AKTIV_DATO("Aktiv dato"),
    PRIORITET("Prioritet"),
    PROSENT("Prosent"),
    STATUS("Status"),
    TEMA("Tema");

    override fun nøkkel(): String {
        return nøkkel
    }
}
