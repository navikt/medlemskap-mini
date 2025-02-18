package no.nav.medlemskap.cucumber.mapping.saf

import io.cucumber.datatable.DataTable
import no.nav.medlemskap.clients.saf.generated.enums.Journalposttype
import no.nav.medlemskap.clients.saf.generated.enums.Journalstatus
import no.nav.medlemskap.clients.saf.generated.enums.Tema
import no.nav.medlemskap.cucumber.BasisDomeneParser
import no.nav.medlemskap.cucumber.Domenenøkkel
import no.nav.medlemskap.cucumber.RadMapper

class SafDomeneSpraakParser : BasisDomeneParser() {
    fun mapDokumentInfoId(dataTable: DataTable): String {
        return mapDataTable(dataTable, DokumentInfoIdMapper())[0]
    }

    fun mapFagsakId(dataTable: DataTable): String {
        return mapDataTable(dataTable, fagsakIdMapper())[0]
    }

    fun mapJournalStatus(dataTable: DataTable): Journalstatus {
        return mapDataTable(dataTable, JournalStatusMapper())[0]
    }

    fun mapDokumentTittel(dataTable: DataTable): String {
        return mapDataTable(dataTable, DokumentTittelMapper())[0]
    }

    fun mapJournalPostId(dataTable: DataTable): String {
        return mapDataTable(dataTable, JournalPostIdMapper())[0]
    }

    fun mapJournalfortAvNavn(dataTable: DataTable): String {
        return mapDataTable(dataTable, JournalfortAvNavnMapper())[0]
    }

    fun mapJournalTema(dataTable: DataTable): Tema {
        return mapDataTable(dataTable, JournalTemaMappeer())[0]
    }

    fun mapJournalPostType(dataTable: DataTable): Journalposttype {
        return mapDataTable(dataTable, JournalPostTypeMapper())[0]
    }

    class JournalPostTypeMapper() : RadMapper<Journalposttype> {
        override fun mapRad(rad: Map<String, String>): Journalposttype {
            return Journalposttype.valueOf(parseString(Domenebegrep.JOURNAL_POST_TYPE, rad))
        }
    }

    class JournalTemaMappeer() : RadMapper<Tema> {
        override fun mapRad(rad: Map<String, String>): Tema {
            return Tema.valueOf(parseString(Domenebegrep.TEMA, rad))
        }
    }

    class JournalStatusMapper() : RadMapper<Journalstatus> {
        override fun mapRad(rad: Map<String, String>): Journalstatus {
            return Journalstatus.valueOf(parseString(Domenebegrep.JOURNAL_STATUS, rad))
        }
    }

    class JournalPostIdMapper() : RadMapper<String> {
        override fun mapRad(rad: Map<String, String>): String {
            return parseString(Domenebegrep.JOURNAL_POST_ID, rad)
        }
    }

    class JournalfortAvNavnMapper() : RadMapper<String> {
        override fun mapRad(rad: Map<String, String>): String {
            return parseString(Domenebegrep.JOURNALFORT_AV_NAVN, rad)
        }
    }

    class DokumentTittelMapper() : RadMapper<String> {
        override fun mapRad(rad: Map<String, String>): String {
            return parseString(Domenebegrep.TITTEL, rad)
        }
    }

    class DokumentInfoIdMapper() : RadMapper<String> {
        override fun mapRad(rad: Map<String, String>): String {
            return parseString(Domenebegrep.DOKUMENT_INFO_ID, rad)
        }
    }

    class fagsakIdMapper() : RadMapper<String> {
        override fun mapRad(rad: Map<String, String>): String {
            return parseString(Domenebegrep.FAGSAKID, rad)
        }
    }
}

enum class Domenebegrep(val nøkkel: String) : Domenenøkkel {
    DOKUMENT_INFO_ID("DokumentInfoId"),
    JOURNAL_POST_ID("JournalpostId"),
    JOURNALFORT_AV_NAVN("Journalført av"),
    JOURNAL_POST_TYPE("Journalposttype"),
    JOURNAL_STATUS("Journalstatus"),
    FAGSAKID("fagsakId"),
    TEMA("Tema"),
    TITTEL("Tittel")
    ;
    override fun nøkkel(): String {
        return nøkkel
    }
}
