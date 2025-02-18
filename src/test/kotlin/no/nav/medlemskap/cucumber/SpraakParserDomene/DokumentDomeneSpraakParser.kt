package no.nav.medlemskap.cucumber.SpraakParserDomene

import io.cucumber.datatable.DataTable
import no.nav.medlemskap.cucumber.BasisDomeneParser
import no.nav.medlemskap.cucumber.Domenenøkkel
import no.nav.medlemskap.cucumber.RadMapper
import no.nav.medlemskap.domene.Journalpost
import no.nav.medlemskap.domene.Sak

object DokumentDomeneSpraakParser : BasisDomeneParser() {
    fun mapJournalpostId(dataTable: DataTable): String {
        return mapDataTable(dataTable, JournalPostIdMapper())[0]
    }

    fun mapJournalfortAvNavn(dataTable: DataTable): String {
        return mapDataTable(dataTable, JournalfortAvNavnMapper())[0]
    }

    fun mapJournalstatus(dataTable: DataTable): String {
        return mapDataTable(dataTable, JournalStatusMapper())[0]
    }

    fun mapJournalposter(dataTable: DataTable): List<Journalpost> {
        return mapDataTable(dataTable, JournalpostMapper())
    }

    fun mapDokumentInfoId(dataTable: DataTable): String {
        return mapDataTable(dataTable, DokumentInfoIdMapper())[0]
    }

    fun mapFagsakId(dataTable: DataTable): String {
        return mapDataTable(dataTable, FagsakIdMapper())[0]
    }

    fun mapTittel(dataTable: DataTable): String {
        return mapDataTable(dataTable, TittelMapper())[0]
    }

    fun mapTema(dataTable: DataTable): String {
        return mapDataTable(dataTable, TemaMapper())[0]
    }

    fun mapJournalposttype(dataTable: DataTable): String {
        return mapDataTable(dataTable, JournalpostTypeMapper())[0]
    }

    class JournalpostTypeMapper : RadMapper<String> {
        override fun mapRad(rad: Map<String, String>): String {
            return parseString(DokumentDomenebegrep.JOURNALPOST_TYPE, rad)
        }
    }

    class TemaMapper : RadMapper<String> {
        override fun mapRad(rad: Map<String, String>): String {
            return parseString(DokumentDomenebegrep.TEMA, rad)
        }
    }

    class DokumentInfoIdMapper : RadMapper<String> {
        override fun mapRad(rad: Map<String, String>): String {
            return parseString(DokumentDomenebegrep.DOKUMENT_INFO_ID, rad)
        }
    }

    class FagsakIdMapper : RadMapper<String> {
        override fun mapRad(rad: Map<String, String>): String {
            return parseString(DokumentDomenebegrep.FAG_SAK_ID, rad)
        }
    }

    class TittelMapper : RadMapper<String> {
        override fun mapRad(rad: Map<String, String>): String {
            return parseString(DokumentDomenebegrep.TITTEL, rad)
        }
    }

    class JournalStatusMapper : RadMapper<String> {
        override fun mapRad(rad: Map<String, String>): String {
            return parseString(DokumentDomenebegrep.JOURNAL_STATUS, rad)
        }
    }

    class JournalPostIdMapper : RadMapper<String> {
        override fun mapRad(rad: Map<String, String>): String {
            return parseString(DokumentDomenebegrep.JOURNAL_POST_ID, rad)
        }
    }

    class JournalfortAvNavnMapper : RadMapper<String> {
        override fun mapRad(rad: Map<String, String>): String {
            return parseString(DokumentDomenebegrep.JOURNALFORT_AV_NAVN, rad)
        }
    }

    class JournalpostMapper : RadMapper<Journalpost> {
        override fun mapRad(rad: Map<String, String>): Journalpost {
            return Journalpost(
                "null",
                null,
                parseString(DokumentDomenebegrep.JOURNALPOST_ID, rad),
                parseValgfriString(DokumentDomenebegrep.JOURNALFORT_AV_NAVN, rad),
                parseValgfriString(DokumentDomenebegrep.TITTEL, rad),
                parseValgfriString(DokumentDomenebegrep.JOURNALPOST_TYPE, rad),
                parseValgfriString(DokumentDomenebegrep.JOURNAL_STATUS, rad),
                parseValgfriString(DokumentDomenebegrep.TEMA, rad),
                Sak(parseValgfriString(DokumentDomenebegrep.FAG_SAK_ID, rad)),
                null
            )
        }
    }

    enum class DokumentDomenebegrep(val nøkkel: String) : Domenenøkkel {
        DOKUMENT_INFO_ID("DokumentInfoId"),
        RELEVANT_DATO("relevantDato"),
        RELEVANT_DATO_TYPE("relevantDatoType"),
        FAG_SAK_ID("fagsakId"),
        JOURNAL_STATUS("Journalstatus"),
        JOURNALPOST_TYPE("JournalpostType"),
        JOURNAL_POST_ID("JournalpostId"),
        JOURNALPOST_ID("JournalpostId"),
        JOURNALFORT_AV_NAVN("Journalført av"),
        PROSENT("Prosent"),
        RELATERT_VED_SIVILSTAND("Relatert ved sivilstand"),
        TEMA("Tema"),
        TITTEL("Tittel");

        override fun nøkkel(): String {
            return nøkkel
        }
    }
}
