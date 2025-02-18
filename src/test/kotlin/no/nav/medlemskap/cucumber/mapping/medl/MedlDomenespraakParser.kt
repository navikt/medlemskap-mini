package no.nav.medlemskap.cucumber.mapping.pdl.medl

import io.cucumber.datatable.DataTable
import no.nav.medlemskap.cucumber.BasisDomeneParser
import no.nav.medlemskap.cucumber.Domenenøkkel
import no.nav.medlemskap.cucumber.RadMapper
import java.time.LocalDate

class MedlDomenespraakParser : BasisDomeneParser() {

    fun mapDekning(dataTable: DataTable): String {
        return mapDataTable(dataTable, DekningMapper())[0]
    }
    fun mapFraOgMed(dataTable: DataTable): LocalDate {
        return mapDataTable(dataTable, FraOgMedMapper())[0]
    }
    fun mapTilOgMed(dataTable: DataTable): LocalDate {
        return mapDataTable(dataTable, TilOgMedMapper())[0]
    }

    fun mapMedlem(dataTable: DataTable): Boolean {
        return mapDataTable(dataTable, MedlemMapper())[0]
    }

    fun mapLovvalg(dataTable: DataTable): String {
        return mapDataTable(dataTable, LovvalgMapper())[0]
    }

    fun mapLovvalgsland(dataTable: DataTable): String {
        return mapDataTable(dataTable, LovvalgslandMapper())[0]
    }

    fun mapStatus(dataTable: DataTable): String {
        return mapDataTable(dataTable, StatusMapper())[0]
    }
}
class LovvalgslandMapper() : RadMapper<String> {
    override fun mapRad(rad: Map<String, String>): String {
        return BasisDomeneParser.parseString(Domenebegrep.LOVVALGSLAND, rad)
    }
}

class StatusMapper() : RadMapper<String> {
    override fun mapRad(rad: Map<String, String>): String {
        return BasisDomeneParser.parseString(Domenebegrep.STATUS, rad)
    }
}

class LovvalgMapper() : RadMapper<String> {
    override fun mapRad(rad: Map<String, String>): String {
        return BasisDomeneParser.parseString(Domenebegrep.LOVVALG, rad)
    }
}
class MedlemMapper() : RadMapper<Boolean> {
    override fun mapRad(rad: Map<String, String>): Boolean {
        return BasisDomeneParser.parseBoolean(Domenebegrep.MEDLEM, rad)
    }
}
class FraOgMedMapper() : RadMapper<LocalDate> {
    override fun mapRad(rad: Map<String, String>): LocalDate {
        return BasisDomeneParser.parseDato(Domenebegrep.FRA_OG_MED, rad)
    }
}

class TilOgMedMapper() : RadMapper<LocalDate> {
    override fun mapRad(rad: Map<String, String>): LocalDate {
        return BasisDomeneParser.parseDato(Domenebegrep.TIL_OG_MED, rad)
    }
}

class DekningMapper() : RadMapper<String> {
    override fun mapRad(rad: Map<String, String>): String {
        return BasisDomeneParser.parseString(Domenebegrep.DEKNING, rad)
    }
}
enum class Domenebegrep(val nøkkel: String) : Domenenøkkel {
    DEKNING("Dekning"),
    LOVVALG("Lovvalg"),
    LOVVALGSLAND("Lovvalgsland"),
    MEDLEM("Medlem"),
    FRA_OG_MED("Gyldig fra og med dato"),
    STATUS("Status"),
    TIL_OG_MED("Gyldig til og med dato")

    ;
    override fun nøkkel(): String {
        return nøkkel
    }
}
