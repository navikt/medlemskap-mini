package no.nav.medlemskap.cucumber.mapping.oppgave

import io.cucumber.datatable.DataTable
import no.nav.medlemskap.clients.oppgave.OppgPrioritet
import no.nav.medlemskap.clients.oppgave.OppgStatus
import no.nav.medlemskap.cucumber.BasisDomeneParser
import no.nav.medlemskap.cucumber.Domenenøkkel
import no.nav.medlemskap.cucumber.RadMapper
import java.time.LocalDate

class OppgaveDomeneSpraakParser : BasisDomeneParser() {

    fun mapAktivDato(dataTable: DataTable): LocalDate {
        return mapDataTable(dataTable, AktivDatoMapper())[0]
    }

    fun mapTema(dataTable: DataTable): String {
        return mapDataTable(dataTable, TemaMappeer())[0]
    }

    fun mapStatus(dataTable: DataTable): OppgStatus {
        return mapDataTable(dataTable, StatusMapper())[0]
    }

    fun mapPrioritet(dataTable: DataTable): OppgPrioritet {
        return mapDataTable(dataTable, PrioritetMapper())[0]
    }

    class AktivDatoMapper() : RadMapper<LocalDate> {
        override fun mapRad(rad: Map<String, String>): LocalDate {
            return parseDato(Domenebegrep.AKTIV_DATO, rad)
        }
    }

    class TemaMappeer() : RadMapper<String> {
        override fun mapRad(rad: Map<String, String>): String {
            return parseString(Domenebegrep.TEMA, rad)
        }
    }

    class PrioritetMapper() : RadMapper<OppgPrioritet> {
        override fun mapRad(rad: Map<String, String>): OppgPrioritet {
            return OppgPrioritet.valueOf(parseString(Domenebegrep.PRIORITET, rad))
        }
    }

    class StatusMapper() : RadMapper<OppgStatus> {
        override fun mapRad(rad: Map<String, String>): OppgStatus {
            return OppgStatus.valueOf(parseString(Domenebegrep.STATUS, rad))
        }
    }
}

enum class Domenebegrep(val nøkkel: String) : Domenenøkkel {
    AKTIV_DATO("AktivDato"),
    PRIORITET("Prioritet"),
    TEMA("Tema"),
    STATUS("Status");

    override fun nøkkel(): String {
        return nøkkel
    }
}
