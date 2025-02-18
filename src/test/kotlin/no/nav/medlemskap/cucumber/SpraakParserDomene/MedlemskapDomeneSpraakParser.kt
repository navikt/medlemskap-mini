package no.nav.medlemskap.cucumber.SpraakParserDomene

import io.cucumber.datatable.DataTable
import no.nav.medlemskap.cucumber.*
import no.nav.medlemskap.domene.Lovvalg
import no.nav.medlemskap.domene.Medlemskap
import no.nav.medlemskap.domene.PeriodeStatus
import java.time.LocalDate

object MedlemskapDomeneSpraakParser : BasisDomeneParser() {

    fun mapDekning(dataTable: DataTable): String {
        return mapDataTable(dataTable, DekningMapper())[0]
    }

    fun mapFraOgMed(dataTable: DataTable): LocalDate {
        return mapDataTable(dataTable, FraOgMedMapper())[0]
    }

    fun mapTilOgMed(dataTable: DataTable): LocalDate {
        return mapDataTable(dataTable, TilOgMedMapper())[0]
    }

    fun mapErMedlem(dataTable: DataTable): Boolean {
        return mapDataTable(dataTable, ErBrukerMedlemMapper())[0]
    }

    fun mapLovvalg(dataTable: DataTable): Lovvalg {
        return mapDataTable(dataTable, LovvalgMapper())[0]
    }

    fun mapLovvalgsland(dataTable: DataTable): String {
        return mapDataTable(dataTable, LovvalgslandMapper())[0]
    }

    fun mapPeriodeStatus(dataTable: DataTable): PeriodeStatus {
        return mapDataTable(dataTable, PeriodeStatusMapper())[0]
    }

    fun mapMedlemskap(dataTable: DataTable): List<Medlemskap> {
        return mapDataTable(dataTable, MedlemskapMapper())
    }

    class FraOgMedMapper : RadMapper<LocalDate> {
        override fun mapRad(rad: Map<String, String>): LocalDate {
            return parseDato(MedlemskapDomenebegrep.GYLDIG_FRA_OG_MED, rad)
        }
    }

    class ErBrukerMedlemMapper : RadMapper<Boolean> {
        override fun mapRad(rad: Map<String, String>): Boolean {
            return parseBoolean(MedlemskapDomenebegrep.ER_MEDLEM, rad)
        }
    }

    class LovvalgslandMapper : RadMapper<String> {
        override fun mapRad(rad: Map<String, String>): String {
            return parseString(MedlemskapDomenebegrep.LOVVALGSLAND, rad)
        }
    }

    class LovvalgMapper : RadMapper<Lovvalg> {
        override fun mapRad(rad: Map<String, String>): Lovvalg {
            return Lovvalg.valueOf(parseString(MedlemskapDomenebegrep.LOVVALG, rad))
        }
    }

    class DekningMapper : RadMapper<String> {
        override fun mapRad(rad: Map<String, String>): String {
            return parseString(MedlemskapDomenebegrep.MEDLEMSKAP_DEKNING, rad)
        }
    }

    class PeriodeStatusMapper : RadMapper<PeriodeStatus> {
        override fun mapRad(rad: Map<String, String>): PeriodeStatus {
            return PeriodeStatus.valueOf(parseString(MedlemskapDomenebegrep.PERIODESTATUS, rad))
        }
    }

    class TilOgMedMapper : RadMapper<LocalDate> {
        override fun mapRad(rad: Map<String, String>): LocalDate {
            return parseDato(MedlemskapDomenebegrep.GYLDIG_TIL_OG_MED, rad)
        }
    }

    class MedlemskapMapper : RadMapper<Medlemskap> {
        override fun mapRad(rad: Map<String, String>): Medlemskap {
            return Medlemskap(
                parseValgfriString(MedlemskapDomenebegrep.DEKNING, rad),
                parseDato(MedlemskapDomenebegrep.FRA_OG_MED_DATO, rad),
                parseDato(MedlemskapDomenebegrep.TIL_OG_MED_DATO, rad),
                parseBoolean(MedlemskapDomenebegrep.ER_MEDLEM, rad),
                DomenespråkParser.parseValgfriLovvalg(MedlemskapDomenebegrep.LOVVALG, rad),
                parseValgfriString(MedlemskapDomenebegrep.LOVVALGSLAND, rad),
                DomenespråkParser.parseValgfriPeriodeStatus(Domenebegrep.PERIODESTATUS, rad),
                "FTL_2-8_1_ledd_a"
            )
        }
    }
}

enum class MedlemskapDomenebegrep(val nøkkel: String) : Domenenøkkel {
    DEKNING("Dekning"),
    ER_MEDLEM("Er medlem"),
    FRA_OG_MED_DATO("Fra og med dato"),
    GYLDIG_FRA_OG_MED("Gyldig fra og med dato"),
    GYLDIG_TIL_OG_MED("Gyldig til og med dato"),
    LOVVALGSLAND("Lovvalgsland"),
    LOVVALG("Lovvalg"),
    MEDLEMSKAP_DEKNING("MedlemskapDekning"),
    PERIODESTATUS("Periodestatus"),
    PROSENT("Prosent"),
    RELATERT_VED_SIVILSTAND("Relatert ved sivilstand"),
    TIL_OG_MED_DATO("Til og med dato");

    override fun nøkkel(): String {
        return nøkkel
    }
}
